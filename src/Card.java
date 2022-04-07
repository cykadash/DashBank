/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Card extends File {
    private String firstName = "John";
    private String lastName = "Smith";
    private char[] pin = {'1', '2', '3', '4'};
    private double balance = 0.0d;
    private int totalTransactions = 0;

    /**
     * Creates a new {@code Card} instance with the given pathname which calls File().
     *
     * @param pathname A pathname string
     * @throws NullPointerException If the {@code pathname} argument is {@code null}
     */
    public Card(String pathname) {
        super(pathname);
        getData();
        setData();

    }

    private void getData() {
        // Assign each variable to match the file.
        Scanner reader = null;
        try {
            reader = new Scanner(new File(this.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
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
    }

    private void setData() {
        try {
            File tmp = new File("src/tmp.txt");
            // Save the transaction info to a temporary file
            Scanner reader = new Scanner(new File(this.getAbsolutePath()));
            FileWriter writer = new FileWriter(tmp);
            while (!Objects.equals(reader.nextLine(), "[Transaction History]")) {
                Thread.onSpinWait();
            }
            reader.nextLine();
            String str;
            while ((str = reader.nextLine()) != null) {
                writer.write(str);
            }

            // Delete the old contents of the old file
            new FileWriter(this.getAbsolutePath(), false).close();
            // Write the new contents to the card
            writer = new FileWriter(this.getAbsolutePath());

            writer.write("[First Name]\t" + firstName + '\n');
            writer.write("[Last Name]\t" + lastName + '\n');
            writer.write(new StringBuilder().append("[PIN]\t\t").append(pin).append('\n').toString());
            writer.write("[Balance]\t" + balance + '\n');
            writer.write("[Transaction History]\n");
            writer.write(String.valueOf(totalTransactions));

            // Write the transaction history
            reader = new Scanner(tmp);
            while ((str = reader.nextLine()) != null) {
                writer.write(str);
            }

            writer.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

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
        double amount = 0;
        String memo = null;
        Date date = null;

        Scanner reader = null;
        try {
            reader = new Scanner(new File(this.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null;
        while (!Objects.equals(reader.nextLine(), "[Transaction History]")) {
            Thread.onSpinWait();
        }

        totalTransactions = Integer.parseInt(reader.nextLine());

        // Make sure the transaction exists
        if (index < totalTransactions) {
            System.out.println("index out of range");
            return null;
        }

        // Skip lines until arrived at index
        for (int i = 0; i < index * 3; i++) reader.nextLine();

        try {
            date = new SimpleDateFormat().parse(reader.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        amount = Double.parseDouble(reader.nextLine());
        memo = reader.nextLine();

        return new Object[]{date, amount, memo};
    }

    public void addTransaction(Date date, double amount, String memo) {
        try {
            FileWriter writer = new FileWriter(this.getAbsolutePath(), true);
            writer.write(date.toString());
            writer.write(String.valueOf(amount));
            writer.write(memo);
            totalTransactions++;
            setData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTotalTransactions() {
        getData();
        return totalTransactions;
    }

    public String getFirstName() {
        getData();
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setData();
    }

    public String getLastName() {
        getData();
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setData();
    }

    public char[] getPin() {
        getData();
        return pin;
    }

    public void setPin(char[] pin) {
        this.pin = pin;
        setData();
    }

    public double getBalance() {
        getData();
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        setData();
    }
}
