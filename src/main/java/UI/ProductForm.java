package UI;

import DB.ProductDAO;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ProductForm extends JDialog {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField quantityField;
    private ProductDAO productDAO;
    private ProductWindow productWindow;
    private Product product;

    /**
     * Constructs a ProductForm dialog.
     *
     * @param productWindow The parent window.
     * @param title         The title of the dialog.
     * @param product       The product object to be edited. Null for creating a new product.
     */
    public ProductForm(ProductWindow productWindow, String title, Product product) {
        this.productWindow = productWindow;
        this.product = product;
        productDAO = new ProductDAO();

        setTitle(title);
        setModal(true);
        setSize(300, 250);
        setLocationRelativeTo(productWindow);

        nameField = new JTextField(20);
        descriptionField = new JTextField(20);
        priceField = new JTextField(20);
        quantityField = new JTextField(20);

        if (product != null) {
            nameField.setText(product.getName());
            descriptionField.setText(product.getDescription());
            priceField.setText(String.valueOf(product.getPrice()));
            quantityField.setText(String.valueOf(product.getQuantity()));
        }

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProduct();
            }
        });

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(saveButton);

        add(panel);
    }

    /**
     * Saves the product information entered in the form.
     */
    private void saveProduct() {
        if (product == null) {
            product = new Product();
        }

        product.setName(nameField.getText());
        product.setDescription(descriptionField.getText());
        product.setPrice(new BigDecimal(priceField.getText()));
        product.setQuantity(Integer.parseInt(quantityField.getText()));

        if (product.getId() == 0) {
            productDAO.insert(product);
        } else {
            productDAO.update(product);
        }

        productWindow.refreshTable();
        dispose();
    }
}
