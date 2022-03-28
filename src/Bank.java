/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */


public class Bank {
    static volatile boolean loggedIn = false;
    static Object[] currentCardData;

    public Bank() {
        // Login window
        LoginPage l = new LoginPage();
        while (!loggedIn) {
            // Wait until logged in
            Thread.onSpinWait();
        }
        l.dispose();
        // Main banking page
        MainPage mp = new MainPage(currentCardData);


    }

    static Object[] parseCardInfo(String firstName, String lastName, String realPinStr, String balanceStr) {
        String[] splitter = firstName.split("]\t");
        firstName = splitter[1];
        splitter = lastName.split("]\t");
        lastName = splitter[1];
        splitter = realPinStr.split("]\t\t");
        char[] realPin = splitter[1].toCharArray();
        splitter = balanceStr.split("]\t");
        double balance = Double.parseDouble(splitter[1]);
        return new Object[]{firstName, lastName, realPin, balance};
    }

    public static void main(String[] args) {
        new Bank();
    }


}
