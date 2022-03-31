package ui.gui;

import model.Card;
import model.MatchingGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Represents the panel that displays the starting, menu, game, and game over screens
public class GamePanel extends JPanel {

    private static final int MARGIN = 30;
    private MatchingGame game;
    private MatchingGameApp gameApp;
    private int width;
    private int height;
    private JLabel guessText;
    private JButton quitButton;
    private JPanel cardPanel;
    private ArrayList<JButton> buttons;

    // EFFECTS: constructs a game panel with a size and background colour of panel,
    //          updates this with the game to be displayed and initializes all components
    public GamePanel(int width, int height, MatchingGame game, MatchingGameApp m) {
        this.game = game;
        this.width = width;
        this.height = height;
        this.gameApp = m;
        this.buttons = new ArrayList<>();
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER, width, MARGIN));
        initializeText();
        initializeQuitButton();
        initializeCardPanel();
        initializeCards();
    }

    // MODIFIES: this
    // EFFECTS: initializes the text and adds it to the panel
    private void initializeText() {
        guessText = new JLabel("Make a guess!");
        guessText.setFont(new Font("Arial", 20, 20));
        guessText.setForeground(Color.WHITE);
        guessText.setBackground(Color.BLACK);
        add(guessText);
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu buttons for the panel
    private void initializeQuitButton() {
        this.quitButton = new JButton("LEAVE GAME");
        quitButton.setActionCommand("leave game");

        JPanel menuArea = new JPanel();
        menuArea.setLayout(new GridLayout(1,0));
        add(menuArea);

        quitButton.addActionListener(gameApp);
        quitButton.setForeground(Color.BLACK);
        quitButton.setBackground(Color.WHITE);
        quitButton.setFont(new Font("Arial", 20, 18));
        quitButton.setFocusable(false);
        menuArea.add(quitButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel that holds cards
    private void initializeCardPanel() {
        cardPanel = new JPanel();
        cardPanel.setPreferredSize(new Dimension(width, height - 200));
        cardPanel.setBackground(Color.BLACK);
        cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, MARGIN, MARGIN));
        add(cardPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: makes a button for each card, customizes it, and places it in its respective location on a panel
    //          holding cards
    public void initializeCards() {
        List<Card> cards = getAllCards();
        for (Card c : reorder(cards)) {
            String n = String.valueOf(c.getLocationNum());
            JButton card;
            if (c.getStatus()) {
                card = new JButton(c.getIdentity());
                card.setActionCommand(n);
                card.setBackground(Color.GRAY);
            } else {
                card = new JButton("  ");
                card.setActionCommand(n);
                card.setBackground(Color.WHITE);
                card.setForeground(Color.BLACK);
                card.addActionListener(gameApp);
            }
            customizeCard(card);
        }
    }

    // EFFECTS: returns a complete list of all the cards in the game
    public List<Card> getAllCards() {
        List<Card> cards = new LinkedList<>();
        cards.addAll(game.getUnmatchedCards());
        cards.addAll(game.getMatchedCards());
        return cards;
    }

    // EFFECTS: returns a list of the same cards but in order of increasing location number
    private List<Card> reorder(List<Card> cards) {
        List<Card> reorderedCards = new LinkedList<>();
        for (int i = 1; i <= game.getCardAmount(); i++) {
            for (Card c : cards) {
                if (c.getLocationNum() == i) {
                    reorderedCards.add(c);
                }
            }
        }
        return reorderedCards;
    }

    // MODIFIES: this
    // EFFECTS: customizes a card and adds it to cardPanel
    private void customizeCard(JButton c) {
        c.setFont(new Font("Arial", Font.PLAIN, 50));
        c.setFocusable(false);
        cardPanel.add(c);
        buttons.add(c);
    }

    // MODIFIES: this
    // EFFECTS: changes the button for the given card to show its identity
    public void showCardIdentity(int locationNum) {
        Card c = game.findCard(locationNum);
        String n = String.valueOf(locationNum);
        for (JButton b : buttons) {
            if (n.equals(b.getActionCommand())) {
                b.setBackground(Color.GREEN);
                b.setText(c.getIdentity());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the button for the given card to hide its identity
    public void hideCardIdentity(int locationNum) {
        String n = String.valueOf(locationNum);
        for (JButton b : buttons) {
            if (n.equals(b.getActionCommand())) {
                b.setBackground(Color.WHITE);
                b.setText("  ");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: alters the button for the given card by changing its style and removing its action listener
    public void removeCardFromPlay(int locationNum) {
        Card c = game.findCard(locationNum);
        String n = String.valueOf(locationNum);
        for (JButton b : buttons) {
            if (n.equals(b.getActionCommand())) {
                b.setBackground(Color.GRAY);
                b.setText(c.getIdentity());
                b.removeActionListener(gameApp);
            }
        }
    }
}
