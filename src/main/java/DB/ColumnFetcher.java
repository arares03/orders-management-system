package DB;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColumnFetcher {

    private static final Logger LOGGER = Logger.getLogger(ColumnFetcher.class.getName());

    /**
     * Fetches column names and creates corresponding objects from the specified table.
     *
     * @param tableName The name of the table.
     * @param type      The type of objects to create.
     * @return A list of created objects.
     */
    public <T> List<T> fetchAndCreateObjects(String tableName, Class<T> type) {
        List<T> objects = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            List<String> columns = fetchColumns(tableName);

            int columnCount = resultSet.getMetaData().getColumnCount();
            if (columnCount > 0) {
                List<T> createdObjects = createObjects(resultSet, type, columns);
                objects.addAll(createdObjects);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while fetching columns and creating objects from the table " + tableName, e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return objects;
    }

    /**
     * Fetches column names from the specified table.
     *
     * @param tableName The name of the table.
     * @return A list of column names.
     */
    public List<String> fetchColumns(String tableName) {
        List<String> columns = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columns.add(resultSet.getMetaData().getColumnName(i));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "An error occurred while fetching columns from the table " + tableName, e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return columns;
    }

    /**
     * Creates objects of the specified type using the provided ResultSet and column names.
     *
     * @param resultSet The ResultSet containing data from the table.
     * @param type      The type of objects to create.
     * @param columns   The list of column names.
     * @return A list of created objects.
     */
    private <T> List<T> createObjects(ResultSet resultSet, Class<T> type, List<String> columns) throws SQLException {
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
                for (String columnName : columns) {
                    Object value = resultSet.getObject(columnName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                 | InvocationTargetException | IntrospectionException e) {
            LOGGER.log(Level.WARNING, "Error creating objects: " + e.getMessage());
        }
        return list;
    }
}
