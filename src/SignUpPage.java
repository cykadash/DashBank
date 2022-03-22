/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;

public class SignUpPage extends JPanel {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JPasswordField pinField;
    private final JPasswordField confirmPinField;
    private final JButton doneButton;
    private final JButton cancelButton;

    /**
     * Creates a new <code>JPanel</code> containing a sign-up form
     *
     * @param frame <code>LoginPage</code> to be used
     */
    public SignUpPage(LoginPage frame) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel instructionLabel = new JLabel("Enter First Name:");
        this.add(instructionLabel);
        firstNameField = new JTextField(10);
        this.add(firstNameField);
        instructionLabel = new JLabel("Enter Last Name:");
        this.add(instructionLabel);
        lastNameField = new JTextField(10);
        this.add(lastNameField);
        instructionLabel = new JLabel("Enter PIN:");
        this.add(instructionLabel);
        pinField = new JPasswordField(4);
        this.add(pinField);
        instructionLabel = new JLabel("Confirm PIN:");
        this.add(instructionLabel);
        confirmPinField = new JPasswordField(4);
        this.add(confirmPinField);
        JPanel line = new JPanel();
        doneButton = new JButton("Done");
        cancelButton = new JButton("Cancel");
        doneButton.addActionListener(frame);
        cancelButton.addActionListener(frame);
        line.add(doneButton);
        line.add(cancelButton);
        this.add(line);
    }

    public JButton getDoneButton() {
        return doneButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void createAccount() {

    }
}
