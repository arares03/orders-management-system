package DB;

import model.Product;
import java.util.List;

/**
 * ProductDAO is a Data Access Object (DAO) class for managing {@link Product} entities.
 * It extends the generic {@link AbstractDAO} class and provides CRUD operations for Product objects.
 */
public class ProductDAO extends AbstractDAO<Product> {

    /**
     * Constructs a new ProductDAO instance.
     */
    public ProductDAO() {
        super();
    }

    /**
     * Finds a Product by its ID.
     *
     * @param id The ID of the product to find.
     * @return The Product with the specified ID, or null if no such product exists.
     */
    @Override
    public Product findById(int id) {
        return super.findById(id);
    }

    /**
     * Inserts a new Product into the database.
     *
     * @param newProduct The Product to insert.
     * @return The inserted Product with an updated ID.
     */
    @Override
    public Product insert(Product newProduct) {
        return super.insert(newProduct);
    }

    /**
     * Updates an existing Product in the database.
     *
     * @param updatedProduct The Product with updated values.
     */
    @Override
    public void update(Product updatedProduct) {
        super.update(updatedProduct);
    }

    /**
     * Deletes a Product from the database by its ID.
     *
     * @param id The ID of the Product to delete.
     */
    @Override
    public void delete(int id) {
        super.delete(id);
    }

    /**
     * Retrieves all Products from the database.
     *
     * @return A list of all Products.
     */
    @Override
    public List<Product> findAll() {
        return super.findAll();
    }
}
