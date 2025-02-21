package DB;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AbstractDAO<T> {
    /**
     * AbstractDAO is a generic Data Access Object (DAO) class providing common database operations.
     * This class uses Java Reflection and JDBC to perform CRUD operations on database entities.
     *
     * @param <T> The type of the entity managed by this DAO.
     */
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Constructor that determines the type of the entity class.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates a SELECT SQL query for a specific field.
     *
     * @param field The field name to filter by.
     * @return The SQL query string.
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Retrieves all records of the entity from the database.
     *
     * @return A list of all entities.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            entities = createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return entities;
    }

    /**
     * Retrieves a record by its ID.
     *
     * @param id The ID of the entity.
     * @return The entity with the specified ID or null if not found.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<T> resultList = createObjects(resultSet);
            if (!resultList.isEmpty()) {
                return resultList.getFirst();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creates a list of entities from the ResultSet.
     *
     * @param resultSet The ResultSet obtained from executing a query.
     * @return A list of entities.
     */
    List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor<?>[] ctors = type.getDeclaredConstructors();
        Constructor<?> ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                 | InvocationTargetException | SQLException | IntrospectionException e) {
            LOGGER.log(Level.WARNING, "Error creating objects: " + e.getMessage());
        }
        return list;
    }

    /**
     * Inserts a new entity into the database.
     *
     * @param entity The entity to be inserted.
     * @return The inserted entity with an updated ID.
     */
    public T insert(T entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO ").append(type.getSimpleName()).append(" (");
        try {
            Field[] fields = type.getDeclaredFields();
            boolean firstField = true;
            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    if (!firstField) {
                        queryBuilder.append(", ");
                    }
                    queryBuilder.append(field.getName());
                    firstField = false;
                }
            }
            queryBuilder.append(") VALUES (");

            for (int i = 0; i < fields.length - 1; i++) {
                if (i > 0) {
                    queryBuilder.append(", ");
                }
                queryBuilder.append("?");
            }
            queryBuilder.append(")");

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(queryBuilder.toString(), Statement.RETURN_GENERATED_KEYS);

            int parameterIndex = 1;
            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    statement.setObject(parameterIndex++, value);
                }
            }

            System.out.println(statement);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting entity failed, no rows affected.");
            }

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                PropertyDescriptor idPropertyDescriptor = new PropertyDescriptor("id", type);
                Method idSetter = idPropertyDescriptor.getWriteMethod();
                idSetter.invoke(entity, id);
            } else {
                throw new SQLException("Inserting entity failed, no ID obtained.");
            }
            return entity;
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (IntrospectionException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param entity The entity with updated values.
     */
    public void update(T entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("UPDATE ").append(type.getSimpleName()).append(" SET ");
            Field[] fields = type.getDeclaredFields();
            boolean firstField = true;
            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    if (!firstField) {
                        queryBuilder.append(", ");
                    }
                    queryBuilder.append(field.getName()).append(" = ?");
                    firstField = false;
                }
            }
            queryBuilder.append(" WHERE id = ?");

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(queryBuilder.toString());

            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (!field.getName().equals("id")) {
                    statement.setObject(parameterIndex++, value);
                }
            }

            PropertyDescriptor idPropertyDescriptor = new PropertyDescriptor("id", type);
            Method idGetter = idPropertyDescriptor.getReadMethod();
            Object idValue = idGetter.invoke(entity);
            statement.setObject(parameterIndex, idValue);


            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating entity failed, no rows affected.");
            }

            LOGGER.log(Level.INFO, type.getName() + " updated successfully!");
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id The ID of the entity to be deleted.
     */
    protected void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String query = "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting entity failed, no rows affected.");
            }

            LOGGER.log(Level.INFO, type.getName() + " deleted successfully!");
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

}
