package ui.gui;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;

// Represents a popup panel that displays options to quit the game
public class QuittingPopUp extends JPanel {

    private static final int WIDTH = 350;
    private static final int HEIGHT = 200;
    private static final int MARGIN = 20;
    private MatchingGameApp gameApp;
    private JLabel text;
    private JButton quitButton;
    private JButton saveQuitButton;
    private JButton cancelButton;

    // EFFECTS: constructs a panel with a size, background, border, and layout, and initializes components
    public QuittingPopUp(MatchingGameApp m) {
        this.gameApp = m;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, MARGIN));
        setBorder(new StrokeBorder(new BasicStroke(), Color.WHITE));
        initializeText();
        initializeButtons();
    }

    // MODIFIES: this
    // EFFECTS: initializes the text and adds it to the panel
    private void initializeText() {
        text = new JLabel("Are you sure you want to quit?");
        text.setFont(new Font("Arial", Font.PLAIN, 20));
        text.setForeground(Color.WHITE);
        text.setBackground(Color.BLACK);
        add(text);
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu buttons for the panel
    private void initializeButtons() {
        quitButton = new JButton("QUIT");
        quitButton.setActionCommand("quit");
        saveQuitButton = new JButton("SAVE & QUIT");
        saveQuitButton.setActionCommand("save and quit");
        cancelButton = new JButton("RETURN TO GAME");
        cancelButton.setActionCommand("cancel");

        JPanel menuArea = new JPanel();
        menuArea.setLayout(new GridLayout(0,1));
        add(menuArea);

        customizeButton(quitButton, menuArea);
        customizeButton(saveQuitButton, menuArea);
        customizeButton(cancelButton, menuArea);
    }

    // MODIFIES: this
    // EFFECTS: customizes a button and adds it to p
    private void customizeButton(JButton b, JPanel p) {
        b.addActionListener(gameApp);
        b.setForeground(Color.BLACK);
        b.setBackground(Color.WHITE);
        b.setFont(new Font("Arial", Font.PLAIN, 18));
        b.setFocusable(false);
        p.add(b);
    }
}
