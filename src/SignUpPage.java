/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class SignUpPage extends JPanel implements ActionListener {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JPasswordField pinField;
    private final JPasswordField confirmPinField;
    private final JButton doneButton;
    private final JButton cancelButton;
    private final LoginPage parentFrame;

    /**
     * Creates a new <code>JPanel</code> containing a sign-up form
     *
     * @param frame <code>LoginPage</code> to be used
     */
    public SignUpPage(LoginPage frame) {
        super();

        parentFrame = frame;

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
        doneButton.addActionListener(this);
        cancelButton.addActionListener(this);
        line.add(doneButton);
        line.add(cancelButton);
        this.add(line);
    }

    public void createAccount(String firstName, String lastName, char[] pin) {
        // Saving the card
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Choose where to save your card.");
        FileFilter ff = new FileNameExtensionFilter("DashBank cards", "card");
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(ff);
        File card = new File(new StringBuilder().append(fc.getCurrentDirectory()).append("/").append(createCardNumber()).append(".card").toString());
        fc.setSelectedFile(card);
        int returnVal = fc.showSaveDialog(parentFrame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                card.createNewFile();
                // Write user information to the file
                FileWriter writer = new FileWriter(card.getAbsolutePath());
                writer.write("[First Name]\t" + firstName + '\n');
                writer.write("[Last Name]\t" + lastName + '\n');
                writer.write(new StringBuilder().append("[PIN]\t\t").append(pin).append('\n').toString());
                writer.write("[Balance]\t" + 0.00d);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: Check logs for details", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            System.out.println("Save as file: " + card.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Card successfully saved at " + card.getAbsolutePath(), "Success!", JOptionPane.INFORMATION_MESSAGE);

        }

    }

    /**
     * Creates a unique card number from a text file of available numbers.
     *
     * @return Random unique 8-digit number
     */
    private char[] createCardNumber() {
        // file containing unique random 8 digit numbers
        File nums = new File("src/availableNumbers.txt");

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nums));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        char[] result = new char[0];
        try {
            result = reader.readLine().toCharArray();
            removeUsedPin(reader, nums);
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] blank = new char[8];
        for (int i = 0; i < blank.length; i++) {
            if (result[i] == '\0') {
                blank[i] = '0';
            } else {
                blank[i] = result[i];
            }
        }
        return blank;
    }

    private void removeUsedPin(BufferedReader reader, File nums) {
        File tmp = new File("src/tmp.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private boolean pinConfirmed() {
        return Arrays.equals(pinField.getPassword(), confirmPinField.getPassword());
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object s = e.getSource();
        if (s == doneButton) {
            if (Objects.equals(firstNameField.getText(), "")) {
                JOptionPane.showMessageDialog(null, "Error: First Name Field is Empty!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else if (Objects.equals(lastNameField.getText(), "")) {
                JOptionPane.showMessageDialog(null, "Error: Last Name Field is Empty!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else if (Objects.equals(pinField.getPassword(), null)) {
                JOptionPane.showMessageDialog(null, "Error: PIN Field is Empty!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else if (!pinConfirmed()) {
                JOptionPane.showMessageDialog(null, "Error: PINs Are Not Equal!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                createAccount(firstNameField.getText(), lastNameField.getText(), pinField.getPassword());
            }
        } else if (s == cancelButton) {
            JPanel contentPane = (JPanel) parentFrame.getContentPane();
            contentPane.removeAll();
            contentPane.add(parentFrame.p);
            contentPane.revalidate();
            contentPane.repaint();
        }
    }
}
