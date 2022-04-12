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
            "Hello, ",
            "Welcome, ",
            "Hey there, ",
            "Long time no see, ",
            "Ahoy, ",
            "Howdy, "
    };
    private final Card card;

    private JPanel p;
    private JLabel balanceLabel;
    private JFormattedTextField withdrawField;
    private JFormattedTextField depositField;
    private JTextField withdrawMemoField;
    private JTextField depositMemoField;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton logoutButton;
    private Component transactionHistoryScroll;

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
        super("DashBank");
        this.setBounds(300, 200, 1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        card = newCard;
        createUI();

        this.setVisible(true);
    }

    private void createUI() {
        // Main Panel

        p = new JPanel();
        p.setLayout(new BorderLayout());
        Random rand = new Random();

        // Generic box layout panel
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
        // Display a random greeting with the user's first name.
        JLabel greetingLabel = new JLabel(greetings[rand.nextInt(6)] + card.getFirstName(), SwingConstants.CENTER);
        greetingLabel.setVerticalAlignment(SwingConstants.TOP);
        box.add(greetingLabel);
        balanceLabel = new JLabel("Balance: \t" + card.getBalance() + "\t$", SwingConstants.CENTER);
        balanceLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        box.add(balanceLabel);
        topPanel.add(box, BorderLayout.WEST);
        box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        // Default label to be used for displaying information
        JLabel genericLabel = new JLabel(card.getFirstName() + " " + card.getLastName(), SwingConstants.CENTER);
        genericLabel.setVerticalAlignment(SwingConstants.TOP);
        box.add(genericLabel);
        logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(this);
        box.add(logoutButton);
        topPanel.add(box, BorderLayout.EAST);

        p.add(topPanel, BorderLayout.NORTH);


        // Deposit/Withdraw Panel
        JTabbedPane transactionPanel = new JTabbedPane();

        // Withdraw panel
        box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        genericLabel = new JLabel("Withdraw:");
        box.add(genericLabel, BorderLayout.CENTER);
        withdrawField = new JFormattedTextField(0d);
        withdrawField.setValue(null);
        withdrawField.setMaximumSize(new Dimension(200, 20));
        box.add(withdrawField, BorderLayout.CENTER);
        genericLabel = new JLabel("Memo: ");
        box.add(genericLabel);
        withdrawMemoField = new JTextField(10);
        withdrawMemoField.setMaximumSize(new Dimension(200, 20));
        box.add(withdrawMemoField);
        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);
        box.add(withdrawButton, BorderLayout.CENTER);
        transactionPanel.addTab("Withdraw", box);

        // Deposit Panel
        box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        genericLabel = new JLabel("Deposit:");
        box.add(genericLabel);
        depositField = new JFormattedTextField(0d);
        withdrawField.setValue(null);
        depositField.setMaximumSize(new Dimension(200, 20));
        box.add(depositField);
        genericLabel = new JLabel("Memo: ");
        box.add(genericLabel);
        depositMemoField = new JTextField(10);
        depositMemoField.setMaximumSize(new Dimension(200, 20));
        box.add(depositMemoField);
        depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);
        box.add(depositButton);
        transactionPanel.addTab("Deposit", box);

        p.add(transactionPanel, BorderLayout.WEST);

        refreshUI();

        p.add(transactionHistoryScroll);

        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setContentPane(p);
    }

    private void refreshUI() {
        withdrawField.setValue(null);
        depositField.setValue(null);
        withdrawMemoField.setText(null);
        depositMemoField.setText(null);
        balanceLabel.setText("Balance: " + card.getBalance() + "$");
        // remove the old transactionHistory if it exists
        if (transactionHistoryScroll != null) this.getContentPane().remove(transactionHistoryScroll);
        // Transaction History
        JPanel transactionHistory = new JPanel();
        transactionHistory.setLayout(new BoxLayout(transactionHistory, BoxLayout.Y_AXIS));
        transactionHistoryScroll = new JScrollPane(transactionHistory);
        // Add each TransactionCard to the panel in chronological order
        int bound = card.getTotalTransactions();
        for (int i = bound - 1; i >= 0; i--) {
            Object[] info = card.getTransaction(i);
            transactionHistory.add(new TransactionCard((String) info[0], (Double) info[1], (String) info[2]));
        }
        p.add(Box.createVerticalGlue());
        // add the new transactionHistory
        p.add(transactionHistoryScroll);
        this.setContentPane(p);
        this.validate();
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
            // Withdraw the specified greater than 0 amount assuming the money is there.
            card.withdraw((Double) withdrawField.getValue(), withdrawMemoField.getText());
            withdrawField.setValue(null);

        } else if (s == depositButton) {
            // Deposit the specified greater than 0 amount.
            card.deposit((Double) depositField.getValue(), depositMemoField.getText());
            depositField.setValue(null);
        } else if (s == logoutButton) {
            Bank.loggedIn = false;
        }
        refreshUI();
    }
}
