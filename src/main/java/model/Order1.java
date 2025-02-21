package model;

import java.sql.Timestamp;

/**
 * The Order1 class represents an order entity in the system.
 * It contains properties such as ID, client ID, order date, and description.
 */
public class Order1 {
    private int id;
    private int client_id;
    private Timestamp order_date;
    private String description;

    /**
     * Default constructor for the Order1 class.
     */
    public Order1() {
    }

    /**
     * Constructor for creating an Order1 with specified client ID, order date, and description.
     *
     * @param client_id    The ID of the client associated with the order.
     * @param order_date   The date and time when the order was placed.
     * @param description A description of the order.
     */
    public Order1(int client_id, Timestamp order_date, String description) {
        this.client_id = client_id;
        this.order_date = order_date;
        this.description = description;
    }

    /**
     * Gets the ID of the order.
     *
     * @return The ID of the order.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the order.
     *
     * @param id The ID of the order.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the client ID associated with the order.
     *
     * @return The client ID associated with the order.
     */
    public int getclient_id() {
        return client_id;
    }

    /**
     * Sets the client ID associated with the order.
     *
     * @param client_id The client ID associated with the order.
     */
    public void setclient_id(int client_id) {
        this.client_id = client_id;
    }

    /**
     * Gets the order date and time.
     *
     * @return The order date and time.
     */
    public Timestamp getorder_date() {
        return order_date;
    }

    /**
     * Sets the order date and time.
     *
     * @param order_date The order date and time.
     */
    public void setorder_date(Timestamp order_date) {
        this.order_date = order_date;
    }

    /**
     * Gets the description of the order.
     *
     * @return The description of the order.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the order.
     *
     * @param description The description of the order.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the order.
     *
     * @return A string representation of the order.
     */
    @Override
    public String toString() {
        return "Order1{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", order_date=" + order_date +
                ", description='" + description + '\'' +
                '}';
    }
}
