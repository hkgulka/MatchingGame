package ui.gui;

import model.Card;
import model.MatchingGame;

import javax.swing.*;
import java.awt.*;

// Represents the panel that displays the starting, menu, game, and game over screens
public class GamePanel extends JPanel {

    private static final int CARD_WIDTH = 60;
    private static final int CARD_HEIGHT = 100;
    private static final int MARGIN = 20;
    private MatchingGame game;
    private int width;
    private int height;

    // EFFECTS: constructs a game panel with a size and background colour of panel,
    //          updates this with the game to be displayed
    public GamePanel(int width, int height, MatchingGame game) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.GRAY);
        this.game = game;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
        if (game.checkGameOver()) {
            gameOver(g);
        }
    }

    // MODIFIES: g
    // EFFECTS: the game is drawn onto g
    private void drawGame(Graphics g) {
        FontMetrics fm;
        if (!game.checkGameOver()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 20, 16));
            fm = g.getFontMetrics();
            centreString("Press (S) to save & quit the game. Press (Q) to quit the game without saving.", g, fm,
                    height / 2 - 2 * MARGIN);
        }
        for (Card c : game.getUnmatchedCards()) {
            int n = c.getLocationNum();
            g.setColor(Color.WHITE);
            g.fillRect((MARGIN + CARD_WIDTH) * n, height - CARD_HEIGHT - MARGIN, CARD_WIDTH, CARD_HEIGHT);
            if (c.isBeingGuessed()) {
                drawCardIdentity(n, c, g);
            } else {
                drawCardNumber(n, c, g);
            }
        }
    }

    // MODIFIES: g
    // EFFECTS: draws the number of a card
    private void drawCardNumber(int n, Card c, Graphics g) {
        String num = String.valueOf(n);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", 20, 40));
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(num);
        g.drawString(num, (MARGIN + CARD_WIDTH) * n + (CARD_WIDTH - width) / 2,
                height - MARGIN - CARD_HEIGHT / 2);
    }


    // MODIFIES: g
    // EFFECTS: draws the identity of a card
    private void drawCardIdentity(int n, Card c, Graphics g) {
        String identity = c.getIdentity();
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", 20, 40));
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(identity);
        g.drawString(identity, (MARGIN + CARD_WIDTH) * n + (CARD_WIDTH - width) / 2,
                height - MARGIN - CARD_HEIGHT / 2);
    }

    // MODIFIES: g
    // EFFECTS: draws "game over", final number of guesses, and final
    //          number of matches onto g
    private void gameOver(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Game Over!", g, fm, height / 2);
        g.setFont(new Font("Arial", 20, 16));
        fm = g.getFontMetrics();
        centreString("You took " + game.getNumGuesses() + " guesses to get " + game.getNumMatches() + "/"
                + game.getNumMatches() + " matches.", g, fm, height / 2 + 50);
    }

    // MODIFIES: g
    // EFFECTS: centres the string str onto g at vertical position y
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (this.width - width) / 2, y);
    }
}
