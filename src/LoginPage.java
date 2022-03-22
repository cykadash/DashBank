/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class LoginPage extends JFrame implements ActionListener {
    private final JTextField nameField;
    private final JPasswordField pinField;
    private final JButton loginButton;
    private final JButton signupButton;
    private final JPanel p;
    private JPanel contentPane;
    private SignUpPage signUpPage;


    /**
     * Constructs a new <code>LoginPage</code> frame that is initially invisible.
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
    public LoginPage() throws HeadlessException {
        super();
        this.setBounds(100, 100, 400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Enter name:");
        p.add(nameLabel);
        nameField = new JTextField(10);
        p.add(nameField);
        JLabel pinLabel = new JLabel("Enter PIN: ");
        p.add(pinLabel);
        pinField = new JPasswordField(4);
        p.add(pinField);
        loginButton = new JButton("Login");
        p.add(loginButton);
        loginButton.addActionListener(this);
        JLabel signupLabel = new JLabel("Don't have an account? Create one!");
        p.add(signupLabel);
        signupButton = new JButton("Sign Up");
        p.add(signupButton);
        signupButton.addActionListener(this);


        this.setContentPane(p);
        this.setVisible(true);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == loginButton) {
            attemptLogin(nameField.getText(), pinField.getPassword());
        } else if (s == signupButton) {
            // Create a sign-up form
            signUpPage = new SignUpPage(this);
            contentPane = (JPanel) this.getContentPane();
            contentPane.removeAll();
            contentPane.add(signUpPage);
            contentPane.revalidate();
            contentPane.repaint();
        } else if (s == signUpPage.getDoneButton()) {
            // create the account
            signUpPage.createAccount();
        } else if (s == signUpPage.getCancelButton()) {
            contentPane = (JPanel) this.getContentPane();
            contentPane.removeAll();
            contentPane.add(p);
            contentPane.revalidate();
            contentPane.repaint();
        }

    }

    /**
     * Attempts a user login
     * <p>
     * TODO: Make the login thru a file I/O system instead of a username
     *
     * @param name The inputted name
     * @param pin  The inputted PIN (must be a character array as opposed to a string for security)
     */
    private void attemptLogin(String name, char[] pin) {


        // Zero out the array for security
        Arrays.fill(pin, '0');
    }
}
