/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Card extends File {
    private String firstName = "John";
    private String lastName = "Smith";
    private char[] pin = {'1', '2', '3', '4'};
    private double balance = 0.0d;


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
            // Delete the old contents of the card
            new FileWriter(this.getAbsolutePath(), false).close();
            // Write the new contents to the card
            FileWriter writer = new FileWriter(this.getAbsolutePath());

            writer.write("[First Name]\t" + firstName + '\n');
            writer.write("[Last Name]\t" + lastName + '\n');
            writer.write(new StringBuilder().append("[PIN]\t\t").append(pin).append('\n').toString());
            writer.write("[Balance]\t" + balance);
            writer.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

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
