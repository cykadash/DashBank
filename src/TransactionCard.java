/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class TransactionCard extends JPanel {

    /**
     * Creates a new <code>JPanel</code> displaying transaction information.
     *
     * @param date   the date of the transaction
     * @param amount the amount that was transferred
     * @param memo   additional information about the transaction
     */
    public TransactionCard(Date date, double amount, String memo) {
        super();
        this.setMaximumSize(new Dimension(500, 300));

        JLabel dateLabel = new JLabel(date.toString());
        // changes the text color of the amountLabel based on whether it was an increase or decrease
        String amountString = amount > 0 ? "\\u001B[32m+" + amount + '$' : "\u001B[31m-" + amount + '$';
        JLabel amountLabel = new JLabel(amountString);
        JLabel memoLabel = new JLabel(memo);
        this.add(dateLabel);
        this.add(amountLabel);
        this.add(memoLabel);
    }
}
