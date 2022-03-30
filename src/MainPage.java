/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MainPage extends JFrame implements ActionListener {
    private final String[] greetings = {
            "Hello ",
            "Welcome, ",
            "Hey there ",
            "Long time no see, ",
            "Ahoy, ",
            "Howdy, "
    };
    private final Card card;
    private JLabel greetingLabel;
    private JLabel balanceLabel;
    private JLabel genericLabel; // Default label to be used for displaying information
    private JFormattedTextField withdrawField;
    private JFormattedTextField depositField;
    private JButton depositButton;
    private JButton withdrawButton;
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
    public MainPage(Card newCard) throws HeadlessException {
        super();
        this.setBounds(300, 200, 1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        card = newCard;
        createUI();

        this.setVisible(true);
    }

    private void createUI() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        Random rand = new Random();
        // Display a random greeting with the user's first name.
        greetingLabel = new JLabel(greetings[rand.nextInt(6)] + card.getFirstName());
        p.add(greetingLabel, BorderLayout.NORTH);
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        balanceLabel = new JLabel("Balance:\t" + card.getBalance() + "\t$");
        center.add(balanceLabel, BorderLayout.CENTER);
        genericLabel = new JLabel("Withdraw:");
        center.add(genericLabel, BorderLayout.CENTER);
        withdrawField = new JFormattedTextField(0.0d);
        center.add(withdrawField, BorderLayout.CENTER);
        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);
        center.add(withdrawButton, BorderLayout.CENTER);
        genericLabel = new JLabel("Deposit:");
        center.add(genericLabel);
        depositField = new JFormattedTextField(0.0d);
        center.add(depositField);
        depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);
        center.add(depositButton);


        p.add(center, BorderLayout.CENTER);

        this.setContentPane(p);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == withdrawButton) {
            // Withdraw specified amount greater than 0.
        } else if (s == depositButton) {
            // Deposit specified amount greater than 0.
        }
    }
}
