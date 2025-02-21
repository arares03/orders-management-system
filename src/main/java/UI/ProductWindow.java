package UI;

import DB.ProductDAO;
import model.Product;

import javax.swing.*;
import java.awt.*;

public class ProductWindow extends JFrame {
    private ProductDAO productDAO;
    private Table<Product> productTable;

    /**
     * Constructs a ProductWindow.
     */
    public ProductWindow() {
        productDAO = new ProductDAO();

        setTitle("Product Operations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        productTable = new Table<>("Product", Product.class);
        add(productTable, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(e -> addProduct());
        JButton editButton = new JButton("Edit Product");
        editButton.addActionListener(e -> editProduct());
        JButton deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(e -> deleteProduct());
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Opens a ProductForm dialog for adding a new product.
     */
    private void addProduct() {
        ProductForm productForm = new ProductForm(this, "Add Product", null);
        productForm.setVisible(true);
    }

    /**
     * Opens a ProductForm dialog for editing the selected product.
     */
    private void editProduct() {
        Product selectedProduct = getSelectedProduct();
        if (selectedProduct != null) {
            ProductForm productForm = new ProductForm(this, "Edit Product", selectedProduct);
            productForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to edit.");
        }
    }

    /**
     * Deletes the selected product from the database and the table.
     */
    private void deleteProduct() {
        Product selectedProduct = getSelectedProduct();
        if (selectedProduct != null) {
            productDAO.delete(selectedProduct.getId());
            productTable.getTableModel().removeRow(productTable.getTable().getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
        }
    }

    /**
     * Retrieves the selected product from the table.
     *
     * @return The selected product.
     */
    private Product getSelectedProduct() {
        int selectedRow = productTable.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            int productId = (int) productTable.getTable().getValueAt(selectedRow, 0);
            return productDAO.findById(productId);
        }
        return null;
    }

    /**
     * Refreshes the table with the latest product data.
     */
    public void refreshTable() {
        productTable.getTableModel().setRowCount(0);
    }
}
