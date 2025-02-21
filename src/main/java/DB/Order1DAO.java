package DB;

import model.Order1;

/**
 * Order1DAO is a Data Access Object (DAO) class for managing {@link Order1} entities.
 * It extends the generic {@link AbstractDAO} class and provides operations for Order1 objects.
 */
public class Order1DAO extends AbstractDAO<Order1> {

    /**
     * Inserts a new Order1 into the database.
     *
     * @param newOrder1 The Order1 object to insert.
     * @return The inserted Order1 with an updated ID.
     */
    public Order1 insert(Order1 newOrder1) {
        return super.insert(newOrder1);
    }
}
