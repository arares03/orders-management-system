package UI;

import DB.Order1DAO;
import DB.ProductDAO;
import model.Client;
import model.Order1;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The OrderStep2Frame class represents the second step in the order creation process.
 * It allows selecting products and quantities for an order, then placing the order.
 * It extends the JFrame class to create a window with Swing components.
 */
public class OrderStep2Frame extends JFrame {
    private List<ProductQuantityPanel> productQuantityPanels;
    private Client client;

    /**
     * Constructs a new OrderStep2Frame instance.
     * Sets the title, default close operation, size, and location of the frame.
     * Initializes the product quantity panels list and creates a panel for products and quantities.
     * Adds action listener to the "Place Order" button.
     *
     * @param client   The client for whom the order is being created.
     * @param products The list of products available for selection.
     */
    public OrderStep2Frame(Client client, List<Product> products) {
        this.client = client;

        setTitle("Select Products and Quantities");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        productQuantityPanels = new ArrayList<>();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (Product product : products) {
            ProductQuantityPanel productQuantityPanel = new ProductQuantityPanel(product);
            productQuantityPanels.add(productQuantityPanel);
            mainPanel.add(productQuantityPanel);
        }

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(e -> placeOrder());

        mainPanel.add(placeOrderButton);

        add(new JScrollPane(mainPanel));

        setVisible(true);
    }

    /**
     * Validates quantities and proceeds with placing the order.
     * If quantities are valid, the order is created in the database and product quantities are updated.
     * Otherwise, an error message is shown.
     */
    private void placeOrder() {
        boolean isValid = true;
        StringBuilder descriptionBuilder = new StringBuilder();

        for (ProductQuantityPanel panel : productQuantityPanels) {
            if (!panel.isValidQuantity()) {
                isValid = false;
                break;
            }
            descriptionBuilder.append(panel.getProduct().getName())
                    .append(": ")
                    .append(panel.getQuantityField().getText())
                    .append("; ");
        }

        if (isValid) {
            String description = descriptionBuilder.toString();

            if (createOrderInDatabase(client, description)) {
                for (ProductQuantityPanel panel : productQuantityPanels) {
                    updateProductQuantity(panel.getProduct(), Integer.parseInt(panel.getQuantityField().getText()));
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create the order. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid quantity for one or more products.");
        }
    }

    /**
     * Creates an order in the database with the given client and description.
     *
     * @param client      The client for whom the order is being created.
     * @param description The description of the order.
     * @return True if the order is successfully created; false otherwise.
     */
    private boolean createOrderInDatabase(Client client, String description) {
        try {
            Order1 order = new Order1();
            order.setclient_id(client.getId());
            order.setorder_date(new Timestamp(System.currentTimeMillis()));
            order.setDescription(description);

            Order1DAO orderDAO = new Order1DAO();

            Order1 createdOrder = orderDAO.insert(order);
            return createdOrder != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates the quantity of a product in the database.
     *
     * @param product  The product to update.
     * @param quantity The new quantity of the product.
     */
    private void updateProductQuantity(Product product, int quantity) {
        try {
            ProductDAO productDAO = new ProductDAO();
            int newQuantity = product.getQuantity() - quantity;

            Product updatedProduct = new Product();
            updatedProduct.setId(product.getId());
            updatedProduct.setName(product.getName());
            updatedProduct.setQuantity(newQuantity);
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setPrice(product.getPrice());


            productDAO.update(updatedProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inner class representing a panel for a product and its quantity.
     */
    private class ProductQuantityPanel extends JPanel {
        private Product product;
        private JTextField quantityField;

        /**
         * Constructs a new ProductQuantityPanel instance.
         * Sets the layout, creates a label for the product name and a text field for quantity.
         * Adds components to the panel.
         *
         * @param product The product associated with this panel.
         */
        public ProductQuantityPanel(Product product) {
            this.product = product;
            setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel nameLabel = new JLabel(product.getName() + " (Quantity: " + product.getQuantity() + "): ");
            quantityField = new JTextField(5);

            add(nameLabel);
            add(quantityField);
        }

        /**
         * Gets the product associated with this panel.
         *
         * @return The product associated with this panel.
         */
        public Product getProduct() {
            return product;
        }

        /**
         * Gets the quantity text field associated with this panel.
         *
         * @return The quantity text field associated with this panel.
         */
        public JTextField getQuantityField() {
            return quantityField;
        }

        /**
         * Checks if the quantity entered in the text field is valid.
         * A valid quantity is a non-negative integer less than or equal to the available quantity of the product.
         *
         * @return True if the quantity is valid; false otherwise.
         */
        public boolean isValidQuantity() {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                return quantity >= 0 && quantity <= product.getQuantity();
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}