package UI;

import javax.swing.*;

/**
 * The OrderWindow class represents a graphical user interface window for order operations.
 * It extends the JFrame class to create a window with Swing components.
 */
public class OrderWindow extends JFrame {

    /**
     * Constructs a new OrderWindow instance.
     * Sets the title, default close operation, size, and location of the window.
     * Creates a label for demonstration and adds it to the frame.
     * Makes the frame visible.
     */
    public OrderWindow() {
        setTitle("Order Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Order Operations", SwingConstants.CENTER);

        add(label);

        setVisible(true);
    }
}
