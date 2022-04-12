/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;
import java.awt.*;

public class TransactionCard extends JPanel {

    /**
     * Creates a new <code>JPanel</code> displaying transaction information.
     *
     * @param date   the date of the transaction
     * @param amount the amount that was transferred
     * @param memo   additional information about the transaction
     */
    public TransactionCard(String date, double amount, String memo) {
        super();
        // any width, height of 100
        this.setMaximumSize(new Dimension(9999, 100));
        this.setMinimumSize(new Dimension(0, 100));

//        JLabel dateLabel = new JLabel(date);
        // change the sign of the amountLabel based on whether it was an increase or decrease
        String amountString = amount + "$";
        JLabel amountLabel = new JLabel(amountString);
        // Change the colour of amountLabel based on increase or decrease
        if (amount > 0) amountLabel.setForeground(Color.green);
        else amountLabel.setForeground(Color.red);

        JLabel memoLabel = new JLabel(memo);
//        this.add(dateLabel);
        this.add(amountLabel);
        this.add(memoLabel);

        this.setBorder(BorderFactory.createTitledBorder(date));
    }
}
