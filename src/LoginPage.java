/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginPage extends JFrame implements ActionListener {
    private JLabel nameLabel;
    private JLabel pinLabel;
    private JLabel signupLabel;
    private JTextField nameField;
    private JTextField pinField;
    private JButton loginButton;
    private JButton signupButton;


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

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        nameLabel = new JLabel("Enter name:");
        p.add(nameLabel);
        nameField = new JTextField(10);
        p.add(nameField);
        pinLabel = new JLabel("Enter PIN: ");
        p.add(pinLabel);
        pinField = new JTextField(4);
        p.add(pinField);
        loginButton = new JButton("Login");
        p.add(loginButton);
        loginButton.addActionListener(this);
        signupLabel = new JLabel("Don't have an account? Create one!");
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
            attemptLogin(nameField.getText(), pinField.getText());
        } else if (s == signupButton) {
            signUp();
        }

    }

    private void signUp() {
    }

    /**
     *  Attempts a user login
     * 
     * @param name The inputted name
     * @param pin The inputted PIN
     */
    private void attemptLogin(String name, String pin) {
        
    }
}
