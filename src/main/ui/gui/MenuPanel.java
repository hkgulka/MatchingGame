package ui.gui;

import model.MatchingGame;

import javax.swing.*;
import java.awt.*;

// Represents a panel that displays a menu with options to add cards to the game or start the game
public class MenuPanel extends JPanel {

    private static final int MARGIN = 40;
    private MatchingGame game;
    private int width;
    private int height;
    private MatchingGameApp gameApp;
    private JLabel text;
    private JButton addCardsButton;
    private JButton playGameButton;

    // EFFECTS: constructs a menu panel with a size and background colour of panel, and initializes components
    public MenuPanel(int width, int height, MatchingGame game, MatchingGameApp m) {
        this.game = game;
        this.width = width;
        this.height = height;
        this.gameApp = m;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, MARGIN));
        JPanel box = new JPanel();
        box.setSize(width, height / 3);
        box.setBackground(Color.BLACK);
        add(box);
        initializeText();
        initializeButtons();
    }

    // MODIFIES: this
    // EFFECTS: initializes the text and adds it to the panel
    private void initializeText() {
        text = new JLabel("There are currently " + game.getCardAmount() / 2 + " pairs of cards in the game.");
        text.setFont(new Font("Arial", Font.PLAIN, 20));
        text.setForeground(Color.WHITE);
        text.setBackground(Color.BLACK);
        add(text);
    }


    // MODIFIES: this
    // EFFECTS: updates the text to reflect the current number of cards in the game
    public void updateText() {
        text.setText("There are currently " + game.getCardAmount() / 2 + " pairs of cards in the game.");
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu buttons for the panel
    private void initializeButtons() {
        this.addCardsButton = new JButton("ADD A PAIR OF CARDS");
        addCardsButton.setActionCommand("add cards");
        this.playGameButton = new JButton("PLAY THE GAME");
        playGameButton.setActionCommand("play game");

        JPanel menuArea = new JPanel();
        menuArea.setLayout(new GridLayout(1,0));
        add(menuArea);

        customizeButton(addCardsButton, menuArea);
        customizeButton(playGameButton, menuArea);
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
}
