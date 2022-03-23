/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;


public class LoginPage extends JFrame implements ActionListener {
    final JPanel p;
    //    private final JTextField nameField;
    private final JPasswordField pinField;
    private final JButton uploadButton;
    private final JButton loginButton;
    private final JButton signupButton;
    private final JFileChooser fc;
    private JPanel contentPane;
    private SignUpPage signUpPage;
    private File card = null;


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

        JLabel nameLabel = new JLabel("Upload your .card file:");
        p.add(nameLabel);
//        nameField = new JTextField(10);
//        p.add(nameField);
        uploadButton = new JButton("Upload");
        p.add(uploadButton);
        uploadButton.addActionListener(this);
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

        fc = new JFileChooser();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == uploadButton) {
            card = uploadFile();
        } else if (s == loginButton) {
            attemptLogin(card, pinField.getPassword());
        } else if (s == signupButton) {
            // Create a sign-up form
            signUpPage = new SignUpPage(this);
            contentPane = (JPanel) this.getContentPane();
            contentPane.removeAll();
            contentPane.add(signUpPage);
            contentPane.revalidate();
            contentPane.repaint();
        }

    }

    /**
     * Uploads a user submitted file
     * <p>
     * TODO: force it to be a .card file maybe
     */
    private File uploadFile() {
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
     * TODO: Make the login thru a file I/O system instead of a username
     *
     * @param card The .card file
     * @param pin  The inputted PIN (must be a character array as opposed to a string for security)
     */
    private void attemptLogin(File card, char[] pin) {


        // Zero out the array for security
        Arrays.fill(pin, '0');
    }
}
