package ui.gui;

import model.MatchingGame;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private static final int MARGIN = 20;
    private MatchingGame game;
    private int width;
    private int height;

    // EFFECTS: constructs a menu panel with a size and background colour of panel
    public MenuPanel(int width, int height, MatchingGame game) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        this.game = game;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMenu(g);
    }

    // MODIFIES: g
    // EFFECTS: draws the menu panel onto g
    private void drawMenu(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 20, 16));
        FontMetrics fm = g.getFontMetrics();
        centreString("There are currently " + game.getCardAmount() / 2 + " pairs of cards in the game.", g, fm,
                height / 2);
        centreString("(A) Add a Pair of Cards to the Game", g, fm,
                height / 2 + 2 * MARGIN);
        centreString("(P) Play the Game", g, fm,
                height / 2 + 3 * MARGIN);
    }

    // MODIFIES: g
    // EFFECTS: centres the string str onto g at vertical position y
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (this.width - width) / 2, y);
    }
}
