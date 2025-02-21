package model;

/**
 * The Client class represents a client entity in the system.
 * It contains properties such as ID, name, email, and address.
 */
public class Client {
    private int id;
    private String name;
    private String email;
    private String address;

    /**
     * Default constructor for the Client class.
     */
    public Client() {
    }

    /**
     * Constructor for creating a Client with specified name, email, and address.
     *
     * @param name    The name of the client.
     * @param email   The email of the client.
     * @param address The address of the client.
     */
    public Client(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    /**
     * Gets the ID of the client.
     *
     * @return The ID of the client.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the client.
     *
     * @param id The ID of the client.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the client.
     *
     * @return The name of the client.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the client.
     *
     * @param name The name of the client.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the client.
     *
     * @return The email of the client.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the client.
     *
     * @param email The email of the client.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the address of the client.
     *
     * @return The address of the client.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the client.
     *
     * @param address The address of the client.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns a string representation of the client.
     *
     * @return The name of the client.
     */
    @Override
    public String toString() {
        return name;
    }
}
