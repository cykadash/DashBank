/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;


public class LoginPage extends JFrame implements ActionListener {
    private final JFileChooser fc;
    JPanel p;
    //    private final JTextField nameField;
    private JPasswordField pinField;
    private JButton uploadButton;
    private JButton loginButton;
    private JButton signupButton;
    private Card card = null;


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
        super("Login Page");
        this.setBounds(600, 300, 400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        createUI();
        this.setVisible(true);

        fc = new JFileChooser();
    }

    void createUI() {
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel cardLabel = new JLabel("Upload your .card file:");
        p.add(cardLabel);
        uploadButton = new JButton("Upload");
        p.add(uploadButton);
        uploadButton.addActionListener(this);
        JLabel pinLabel = new JLabel("Enter PIN: ");
        p.add(pinLabel);
        pinField = new JPasswordField(4);
        pinField.setMaximumSize(new Dimension(60, 20));
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
        if (s == uploadButton) card = new Card(Objects.requireNonNull(uploadFile()).getAbsolutePath(), false);
        else if (s == loginButton) attemptLogin(card, pinField.getPassword());
        else if (s == signupButton) {
            // Create a sign-up form
            SignUpPage signUpPage = new SignUpPage(this);
            JPanel contentPane = (JPanel) this.getContentPane();
            contentPane.removeAll();
            contentPane.add(signUpPage);
            contentPane.revalidate();
            contentPane.repaint();
        }

    }

    /**
     * Uploads a user submitted file
     * <p>
     *
     * @return the uploaded file
     */
    private File uploadFile() {
        fc.setDialogTitle("Open a card.");
        FileFilter ff = new FileNameExtensionFilter("DashBank cards", "card");
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(ff);
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Opening: " + fc.getSelectedFile().getName() + ".");
            uploadButton.setText("Uploaded! Press again to upload different card");
            return fc.getSelectedFile();
        } else {
            System.out.println("Open command cancelled by user.");
        }
        return null;

    }

    /**
     * Attempts a user login
     * <p>
     *
     * @param card The .card file
     * @param pin  The inputted PIN (must be a character array as opposed to a string for security)
     */
    private void attemptLogin(Card card, char[] pin) {
        Scanner reader = null;
        try {
            reader = new Scanner(card);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: No card uploaded.", "Error!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        if (Arrays.equals(card.getPin(), pin)) {
            Bank.loggedIn = true;
            Bank.card = card;
        }


        // Zero out the array for security
        Arrays.fill(pin, '0');
    }
}
