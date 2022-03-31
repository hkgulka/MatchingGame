package ui.gui;

import javafx.scene.shape.Rectangle;

import javax.swing.*;
import java.awt.*;

// Represents a panel that shows up upon starting the application
public class StartPanel extends JPanel {

    private static final int MARGIN = 24;
    private int width;
    private int height;
    private JLabel icon;
    private JTextArea text;
    private JButton newGameButton;
    private JButton loadGameButton;
    private MatchingGameApp gameApp;

    // EFFECTS: constructs a start panel with a corresponding game app, a size and background colour of panel, an
    //          icon image, a text label, and two menu buttons
    public StartPanel(int width, int height, MatchingGameApp m) {
        this.width = width;
        this.height = height;
        this.gameApp = m;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 40));
        JPanel box = new JPanel();
        box.setSize(width, height / 2);
        box.setBackground(Color.BLACK);
        add(box);
        ImageIcon image = new ImageIcon("MatchingGame.png");
        this.icon = new JLabel(image);
        add(icon);
        initializeText();
        initializeButtons();
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu buttons for the panel
    private void initializeButtons() {
        this.newGameButton = new JButton("START A NEW GAME");
        newGameButton.setActionCommand("new game");
        this.loadGameButton = new JButton("LOAD A GAME FROM SAVE");
        loadGameButton.setActionCommand("load game");

        JPanel menuArea = new JPanel();
        menuArea.setLayout(new GridLayout(0,1));
        add(menuArea);

        customizeButton(newGameButton, menuArea);
        customizeButton(loadGameButton, menuArea);
    }

    // MODIFIES: this
    // EFFECTS: customizes a button and adds it to p
    private void customizeButton(JButton b, JPanel p) {
        b.addActionListener(gameApp);
        b.setForeground(Color.BLACK);
        b.setBackground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 18));
        b.setFocusable(false);
        p.add(b);
    }

    // MODIFIES: this
    // EFFECTS: initializes and customizes a text component
    public void initializeText() {
        text = new JTextArea(
                "Each card in the game has an 'identity'. Your goal is to guess all "
                        + "of the pairs of cards with matching identities, without seeing those "
                        + "identities all at once. When you guess a card, its identity will be "
                        + "temporarily revealed to you -- try to remember it for later guesses!"
        );
        text.setSize(width / 2, height / 4);
        text.setFont(new Font("Arial", Font.PLAIN, 20));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setForeground(Color.WHITE);
        text.setBackground(Color.BLACK);
        add(text);
    }
}
