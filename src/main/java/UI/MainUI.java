package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The MainUI class represents the main user interface window of the application.
 * It extends the JFrame class to create a window with Swing components.
 */
public class MainUI extends JFrame {

    /**
     * Constructs a new MainUI instance.
     * Sets the title, default close operation, size, and location of the window.
     * Creates buttons for client, product, and order operations.
     * Adds action listeners to the buttons to open corresponding windows or frames.
     * Adds the buttons to a panel and adds the panel to the frame.
     * Makes the frame visible.
     */
    public MainUI() {
        setTitle("Main Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JButton clientButton = new JButton("Client");
        JButton productButton = new JButton("Product");
        JButton orderButton = new JButton("Order");

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openClientWindow();
            }
        });

        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProductWindow();
            }
        });

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOrderStep1Frame();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(clientButton);
        panel.add(productButton);
        panel.add(orderButton);

        add(panel);

        setVisible(true);
    }

    /**
     * Opens the ClientWindow.
     */
    private void openClientWindow() {
        new ClientWindow().setVisible(true);
    }

    /**
     * Opens the ProductWindow.
     */
    private void openProductWindow() {
        new ProductWindow().setVisible(true);
    }

    /**
     * Opens the OrderStep1Frame.
     */
    private void openOrderStep1Frame() {
        new OrderStep1Frame().setVisible(true);
    }
}
