package ui.gui;

import model.MatchingGame;

import javax.swing.*;
import java.awt.*;

// Represents the panel that displays the guesses and matches made in the game
public class ScorePanel extends JPanel {
    private static final int LABEL_WIDTH = 200;
    private static final int LABEL_HEIGHT = 30;
    private MatchingGame game;
    private JLabel guessesLabel;
    private JLabel matchesLabel;

    // EFFECTS: constructs a score panel with background colour and draws the initial labels,
    //          and also updates this with the game guesses and matches to be displayed
    public ScorePanel(MatchingGame game) {
        this.game = game;
        setBackground(Color.WHITE);
        guessesLabel = new JLabel("Guesses made: " + game.getNumGuesses());
        guessesLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        matchesLabel = new JLabel("Matches made: " + game.getNumMatches());
        matchesLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        add(guessesLabel);
        add(Box.createHorizontalStrut(10));
        add(matchesLabel);
    }

    // MODIFIES: this
    // EFFECTS: updates the number of guesses and matches to match those of current game state
    public void update() {
        guessesLabel.setText("Guesses made: " + game.getNumGuesses());
        matchesLabel.setText("Matches made: " + game.getNumMatches());
        repaint();
    }
}
