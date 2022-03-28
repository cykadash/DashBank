/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame implements ActionListener {
    private JLabel greetingLabel;
    private JLabel balanceLabel;
    private JLabel genericLabel; // Default label to be used for displaying information
    private JFormattedTextField withdrawlAmountField;
    private JFormattedTextField depositAmountField;
    private JButton depositButton;
    private JButton withdrawlButton;
    private JButton logoutButton;
    private ScrollPane transactionHistory;

    /**
     * Constructs a new <code>MainPage</code> that is initially invisible.
     * <p>
     * The main page is where most of the information is displayed.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     *                           returns true.
     * @see GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public MainPage(Object[] cardData) throws HeadlessException {
        super();
        this.setBounds(300, 200, 1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createUI();

        this.setVisible(true);
    }

    private void createUI() {
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
