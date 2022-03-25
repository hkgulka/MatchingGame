package ui.gui;

import model.MatchingGame;

import javax.swing.*;
import java.awt.*;

// Represents a panel that shows up upon starting the application
public class StartPanel extends JPanel {

    private static final int MARGIN = 20;
    private int width;
    private int height;

    // EFFECTS: constructs a start panel with a size and background colour of panel
    public StartPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawStart(g);
    }

    // MODIFIES: g
    // EFFECTS: draws the starting panel onto g
    private void drawStart(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("Welcome to the Matching Game!", g, fm, height / 2 - 2 * MARGIN);
        g.setFont(new Font("Arial", 20, 16));
        fm = g.getFontMetrics();
        centreString("Each card in the game has an 'identity'. Your goal is to guess all", g, fm,
                height / 2);
        centreString("of the pairs of cards with matching identities, without seeing those", g, fm,
                height / 2 + MARGIN);
        centreString("identities all at once. When you guess a card, its identity will be", g, fm,
                height / 2 + 2 * MARGIN);
        centreString("temporarily revealed to you -- try to remember it for later guesses!", g, fm,
                height / 2 + 3 * MARGIN);
        centreString("(N) Start a New Game", g, fm,
                height / 2 + 5 * MARGIN);
        centreString("(L) Load a Previous Game from File", g, fm,
                height / 2 + 6 * MARGIN);
    }

    // MODIFIES: g
    // EFFECTS: centres the string str onto g at vertical position y
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (this.width - width) / 2, y);
    }
}
