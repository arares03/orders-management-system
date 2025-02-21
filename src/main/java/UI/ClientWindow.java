package UI;

import DB.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 * The ClientWindow class represents a graphical user interface window for client operations.
 * It extends the JFrame class to create a window with Swing components.
 */
public class ClientWindow extends JFrame {
    /**
     * Constructs a new ClientWindow instance.
     * Sets the title, default close operation, size, and location of the window.
     * Initializes the table model, loads clients from the database, and sets up buttons for client operations.
     * Makes the frame visible.
     */
    public ClientWindow() {
        setTitle("Client Operations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        Table<Client> clientTable = new Table<>("client", Client.class);
        add(clientTable, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Client");
        JButton editButton = new JButton("Edit Client");
        JButton deleteButton = new JButton("Delete Client");

        addButton.addActionListener(e -> addClient());
        editButton.addActionListener(e -> editClient());
        deleteButton.addActionListener(e -> deleteClient());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
        setVisible(true);
    }

    /**
     * Opens a dialog to add a new client.
     */
    private void addClient() {
        ClientForm clientForm = new ClientForm(this, "Add Client", null);
        clientForm.setVisible(true);
    }

    /**
     * Opens a dialog to edit the selected client.
     */
    private void editClient() {
        int selectedRow = getClientTable().getSelectedRow();
        if (selectedRow >= 0) {
            int clientId = (int) getClientTable().getValueAt(selectedRow, 0);
            Client client = getClientDAO().findById(clientId);
            ClientForm clientForm = new ClientForm(this, "Edit Client", client);
            clientForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a client to edit.");
        }
    }

    /**
     * Deletes the selected client from the database and updates the client table.
     */
    private void deleteClient() {
        int selectedRow = getClientTable().getSelectedRow();
        if (selectedRow >= 0) {
            int clientId = (int) getClientTable().getValueAt(selectedRow, 0);
            getClientDAO().delete(clientId);
            getClientTableModel().removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a client to delete.");
        }
    }

    /**
     * Refreshes the client table by clearing its contents and reloading clients from the database.
     */
    public void refreshTable() {
        getClientTableModel().setRowCount(0);
        loadClients();
    }

    /**
     * Loads clients from the database and populates the client table.
     */
    private void loadClients() {
        List<Client> clients = getClientDAO().findAll();
        for (Client client : clients) {
            getClientTableModel().addRow(new Object[]{client.getId(), client.getName(), client.getEmail(), client.getAddress()});
        }
    }

    /**
     * Getter for the client table.
     *
     * @return The client table.
     */
    private JTable getClientTable() {
        return ((Table<Client>) getContentPane().getComponent(0)).getTable();
    }

    /**
     * Getter for the client DAO.
     *
     * @return The client DAO.
     */
    private ClientDAO getClientDAO() {
        return new ClientDAO();
    }

    /**
     * Getter for the client table model.
     *
     * @return The client table model.
     */
    private DefaultTableModel getClientTableModel() {
        return ((Table<Client>) getContentPane().getComponent(0)).getTableModel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientWindow::new);
    }
}
