/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */


@SuppressWarnings("InstantiationOfUtilityClass")
public class Bank {
    static volatile boolean loggedIn = false;
    static Card card;

    public Bank() {
        // Login window
        LoginPage l = new LoginPage();
        while (!loggedIn) {
            // Wait until logged in
            Thread.onSpinWait();
        }
        l.dispose();
        // Main banking page
        MainPage mp = new MainPage(card);
        // Detect logout
        while (loggedIn) {
            Thread.onSpinWait();
        }
        mp.dispose();
        new Bank();
        System.exit(0);


    }

    public static void main(String[] args) {
        new Bank();
    }


}
