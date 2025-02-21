package UI;

import DB.ClientDAO;
import DB.ProductDAO;
import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The OrderStep1Frame class represents the first step in the order creation process.
 * It extends the JFrame class to create a window with Swing components.
 */
public class OrderStep1Frame extends JFrame {
    private JComboBox<Client> clientComboBox;
    private ClientDAO clientDAO;

    /**
     * Constructs a new OrderStep1Frame instance.
     * Sets the title, default close operation, size, and location of the frame.
     * Initializes components, loads clients into a combo box, and adds an action listener to the "Next" button.
     * Creates a panel for components and adds it to the frame.
     */
    public OrderStep1Frame() {
        setTitle("Select Client");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        clientDAO = new ClientDAO();

        clientComboBox = new JComboBox<>();
        JButton nextButton = new JButton("Next");

        loadClients();

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextStep();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Select Client:"), BorderLayout.NORTH);
        panel.add(clientComboBox, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);

        add(panel);
    }

    /**
     * Loads clients from the database and populates the client combo box.
     */
    private void loadClients() {
        List<Client> clients = clientDAO.findAll();
        for (Client client : clients) {
            clientComboBox.addItem(client);
        }
    }

    /**
     * Moves to the next step in the order creation process.
     * Closes the current frame and opens the OrderStep2Frame.
     */
    private void nextStep() {
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        if (selectedClient != null) {
            dispose();
            new OrderStep2Frame(selectedClient, getAllProducts());
        } else {
            JOptionPane.showMessageDialog(this, "Please select a client.");
        }
    }

    /**
     * Retrieves all products from the database.
     *
     * @return A list of all products.
     */
    private List<Product> getAllProducts() {
        ProductDAO productDAO = new ProductDAO();
        return productDAO.findAll();
    }
}
