/*
 * Copyright (c) Dashiell Giguere 2022.
 * A simple banking program
 */

import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {
    /**
     * Constructs a new <code>MainPage</code> that is initially invisible.
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
    public MainPage(Object[] cardData) throws HeadlessException {
        super();
        this.setBounds(300, 200, 1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        this.setVisible(true);
    }
}
