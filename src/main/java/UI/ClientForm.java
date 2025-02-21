package UI;

import model.Client;
import DB.ClientDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The ClientForm class represents a dialog for adding or editing client information.
 * It extends JDialog and contains text fields for entering client details.
 */
public class ClientForm extends JDialog {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField addressField;
    private ClientDAO clientDAO;
    private ClientWindow clientWindow;
    private Client client;

    /**
     * Constructs a new ClientForm dialog.
     *
     * @param clientWindow The parent ClientWindow.
     * @param title        The title of the dialog.
     * @param client       The client to edit, or null for adding a new client.
     */
    public ClientForm(ClientWindow clientWindow, String title, Client client) {
        this.clientWindow = clientWindow;
        this.client = client;
        clientDAO = new ClientDAO();

        setTitle(title);
        setModal(true);
        setSize(300, 200);
        setLocationRelativeTo(clientWindow);

        nameField = new JTextField(20);
        emailField = new JTextField(20);
        addressField = new JTextField(20);

        if (client != null) {
            nameField.setText(client.getName());
            emailField.setText(client.getEmail());
            addressField.setText(client.getAddress());
        }

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveClient();
            }
        });

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(saveButton);

        add(panel);
    }

    /**
     * Saves the client information to the database.
     */
    private void saveClient() {
        if (client == null) {
            client = new Client();
        }

        client.setName(nameField.getText());
        client.setEmail(emailField.getText());
        client.setAddress(addressField.getText());

        if (client.getId() == 0) {
            clientDAO.insert(client);
        } else {
            clientDAO.update(client);
        }

        clientWindow.refreshTable();
        dispose();
    }
}
