package ui.gui;

import model.MatchingGame;

import javax.swing.*;
import java.awt.*;

// Represents a panel that is displayed upon completing the game
public class GameOverPanel extends JPanel {
    private static final int MARGIN = 40;
    private MatchingGame game;
    private JLabel gameOverText;
    private JLabel statsText;

    // EFFECTS: constructs a panel with a size and background, and initializes components
    public GameOverPanel(int width, int height, MatchingGame game) {
        this.game = game;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, MARGIN));
        JPanel box = new JPanel();
        box.setSize(width, height / 3);
        box.setBackground(Color.BLACK);
        add(box);
        initializeGameOverText();
        initializeStatsText();
    }

    // MODIFIES: this
    // EFFECTS: initializes the text that says "game over" and adds it to the panel
    private void initializeGameOverText() {
        gameOverText = new JLabel("Game Over!");
        gameOverText.setFont(new Font("Arial", Font.BOLD, 50));
        gameOverText.setForeground(Color.WHITE);
        gameOverText.setBackground(Color.BLACK);
        add(gameOverText);
    }

    // MODIFIES: this
    // EFFECTS: initializes the text that shows game stats and adds it to the panel
    private void initializeStatsText() {
        statsText = new JLabel("You took " + game.getNumGuesses() + " guesses to get " + game.getNumMatches() + "/"
                + game.getNumMatches() + " matches.");
        statsText.setFont(new Font("Arial", Font.PLAIN, 20));
        statsText.setForeground(Color.WHITE);
        statsText.setBackground(Color.BLACK);
        add(statsText);
    }
}
