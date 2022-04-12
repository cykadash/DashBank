/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Card extends File {
    private String firstName = "John";
    private String lastName = "Smith";
    private char[] pin = {'1', '2', '3', '4'};
    private double balance = 0.0d;
    private int totalTransactions = 0;

    /**
     * Creates a new {@code Card} instance with the given pathname which calls File().
     *
     * @param pathname The path of the file.
     * @param newFile  {@code true} if card is new, {@code false} if not.
     * @throws NullPointerException If the {@code pathname} argument is {@code null}
     */
    public Card(String pathname, boolean newFile) {
        super(pathname);
        if (!newFile) {
            try {
                getData();
                setData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void withdraw(double amount, String memo) {
        if (amount > 0) {
            if (amount <= balance) {
                balance = balance - amount;
                memo = "Withdrawal - " + memo;
                addTransaction(new Date(), -amount, memo);
            } else
                JOptionPane.showMessageDialog(null, "Error: Account does not have enough funds", "Error!", JOptionPane.ERROR_MESSAGE);
        } else
            JOptionPane.showMessageDialog(null, "Error: Amount must be greater than 0.\n User inputted: " + amount + ".", "Error!", JOptionPane.ERROR_MESSAGE);
    }

    public void deposit(Double amount, String memo) {
        if (amount > 0) {
            balance = balance + amount;
            memo = "Deposit - " + memo;
            addTransaction(new Date(), amount, memo);
        } else
            JOptionPane.showMessageDialog(null, "Error: Amount must be greater than 0.\n User inputted: " + amount + ".", "Error!", JOptionPane.ERROR_MESSAGE);
    }

    private void getData() throws IOException, NoSuchElementException {
        // Assign each variable to match the file.
        Scanner reader;
        reader = new Scanner(this);
        String str = reader.nextLine();
        String[] splitter = str.split("]\t");
        firstName = splitter[1];
        str = reader.nextLine();
        splitter = str.split("]\t");
        lastName = splitter[1];
        str = reader.nextLine();
        splitter = str.split("]\t\t");
        pin = splitter[1].toCharArray();
        str = reader.nextLine();
        splitter = str.split("]\t");
        balance = Double.parseDouble(splitter[1]);
        reader.nextLine();
        totalTransactions = Integer.parseInt(reader.nextLine());
    }

    private void setData() throws NoSuchElementException, IOException {
        File tmp = new File("src/tmp.txt");
        // Save the transaction info to a temporary file
        Scanner reader = new Scanner(this);
        FileWriter writer = new FileWriter(tmp);
        while (!Objects.equals(reader.nextLine(), "[Transaction History]")) {
            Thread.onSpinWait();
        }
        reader.nextLine();
        while (reader.hasNextLine()) {
            writer.write('\n' + reader.nextLine());
        }
        writer.close();

        // Delete the old contents of the old file
        new FileWriter(this, false).close();
        // Write the new contents to the card
        writer = new FileWriter(this);

        writer.write("[First Name]\t" + firstName + '\n');
        writer.write("[Last Name]\t" + lastName + '\n');
        //noinspection StringBufferReplaceableByString
        writer.write(new StringBuilder().append("[PIN]\t\t").append(pin).append('\n').toString());
        writer.write("[Balance]\t" + balance + '\n');
        writer.write("[Transaction History]\n");
        writer.write(String.valueOf(totalTransactions));

        // Write the transaction history
        reader = new Scanner(tmp);
        String line;
        while (reader.hasNextLine()) {
            if (!Objects.equals(line = reader.nextLine(), ""))
                writer.write('\n' + line);
        }

        writer.close();
        new FileWriter(tmp, false).close();

    }

    public Object[] getTransaction(int index) {
        /*
            Transactions are handled within the file as

            the header is:
            [Transaction History]
            totalTransactions

            each transaction is formatted as:
            <date>
            +/- amount
            memo

         */

        // Read the file and find the transaction at specified index
        double amount;
        String memo;
        String date;

        Scanner reader = null;
        try {
            reader = new Scanner(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        while (!Objects.equals(reader.nextLine(), "[Transaction History]")) {
            Thread.onSpinWait();
        }

        totalTransactions = Integer.parseInt(reader.nextLine());

        // Make sure the transaction exists
        if (index > totalTransactions) {
            System.out.println("index out of range");
            return null;
        }

        // Skip lines until arrived at index
        for (int i = 0; i < index * 3; i++) reader.nextLine();

        date = reader.nextLine();
        amount = Double.parseDouble(reader.nextLine());
        memo = reader.nextLine();

        return new Object[]{date, amount, memo};
    }

    public void addTransaction(Date date, double amount, String memo) {
        try {
            FileWriter writer = new FileWriter(this, true);
            writer.write('\n' + date.toString() + '\n');
            writer.write(String.valueOf(amount) + '\n');
            writer.write(memo + '\n');
            writer.close();
            totalTransactions++;
            setData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTotalTransactions() {
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalTransactions;
    }

    public String getFirstName() {
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return firstName;
    }

    public void setFirstName(String firstName) throws IOException {
        this.firstName = firstName;
        setData();
    }

    public String getLastName() {
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastName;
    }

    public void setLastName(String lastName) throws IOException {
        this.lastName = lastName;
        setData();
    }

    public char[] getPin() {
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pin;
    }

    public void setPin(char[] pin) throws IOException {
        this.pin = pin;
        setData();
    }

    public double getBalance() {
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        try {
            setData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
