package DB;

import model.Client;
import java.util.List;

/**
 * ClientDAO is a Data Access Object (DAO) class for managing {@link Client} entities.
 * It extends the generic {@link AbstractDAO} class and provides CRUD operations for Client objects.
 */
public class ClientDAO extends AbstractDAO<Client> {

    /**
     * Constructs a new ClientDAO instance.
     */
    public ClientDAO() {
        super();
    }

    /**
     * Finds a Client by its ID.
     *
     * @param client_id The ID of the client to find.
     * @return The Client with the specified ID, or null if no such client exists.
     */
    public Client findById(int client_id) {
        return super.findById(client_id);
    }

    /**
     * Inserts a new Client into the database.
     *
     * @param newClient The Client to insert.
     * @return The inserted Client with an updated ID.
     */
    public Client insert(Client newClient) {
        return super.insert(newClient);
    }

    /**
     * Updates an existing Client in the database.
     *
     * @param clientWithUpdatedValues The Client with updated values.
     */
    public void update(Client clientWithUpdatedValues) {
        super.update(clientWithUpdatedValues);
    }

    /**
     * Deletes a Client from the database by its ID.
     *
     * @param id The ID of the Client to delete.
     */
    public void delete(int id) {
        super.delete(id);
    }

    /**
     * Retrieves all Clients from the database.
     *
     * @return A list of all Clients.
     */
    public List<Client> findAll() {
        return super.findAll();
    }
}
