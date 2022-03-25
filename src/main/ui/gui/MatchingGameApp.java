package ui.gui;

import model.MatchingGame;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents a matching game application with a graphical user interface
public class MatchingGameApp extends JFrame {

    private static final int INTERVAL = 20;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private MatchingGame game;
    private StartPanel stp;
    private MenuPanel mp;
    private GamePanel gp;
    private ScorePanel scp;
    private Timer timer;

    private static final String JSON_STORE = "./data/MatchingGame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean inStart;
    private boolean inMenu;
    private boolean inGame;
    private List<Integer> guesses;

    // EFFECTS: constructs a matching game app with a starting window
    public MatchingGameApp() {
        super("Matching Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        game = new MatchingGame();
        stp = new StartPanel(WIDTH, HEIGHT);
        mp = new MenuPanel(WIDTH, HEIGHT, game);
        gp = new GamePanel(WIDTH, HEIGHT, game);
        scp = new ScorePanel(game);
        add(stp);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        inStart = true;
        inMenu = false;
        inGame = false;
        addTimer();
        timer.start();
        guesses = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: centres the window on the desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // EFFECTS: initializes a timer that updates the game each
    //          INTERVAL milliseconds
    private void addTimer() {
        timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                mp.repaint();
                gp.repaint();
                scp.update();

            }
        });
    }

    // Represents a key handler to respond to key events
    private class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_Q) {
                System.exit(0);
            } else if (keyCode == KeyEvent.VK_S) {
                saveToFile();
                System.exit(0);
            } else if (keyCode == KeyEvent.VK_N && inStart) {
                newGame();
            } else if (keyCode == KeyEvent.VK_L && inStart) {
                loadGameFromSave();
            } else if (keyCode == KeyEvent.VK_A && inMenu) {
                game.addCardPair();
            } else if (keyCode == KeyEvent.VK_P && inMenu) {
                playGame();
            } else if (inGame) {
                try {
                    int i = Integer.parseInt(String.valueOf(e.getKeyChar()));
                    handleNumberGuessed(i);
                } catch (NumberFormatException n) {
                    // nothing happens
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the current state of the game to JSON_STORE
    private void saveToFile() {
        try {
            this.jsonWriter.open();
            this.jsonWriter.write(this.game);
            this.jsonWriter.close();
        } catch (FileNotFoundException f) {
            // does nothing (no file to save to)
        }
    }


    // MODIFIES: this
    // EFFECTS: plays the new matching game and advances beyond the starting screen
    private void newGame() {
        inStart = false;
        inMenu = true;
        remove(stp);
        add(mp);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: loads the game from the save file and plays it
    private void loadGameFromSave() {
        try {
            this.game = jsonReader.read();
            inStart = false;
            remove(stp);
            playGame();
        } catch (IOException io) {
            // does nothing (no file saved)
        }
    }


    // MODIFIES: this
    // EFFECTS: plays the matching game
    private void playGame() {
        if (inMenu) {
            remove(mp);
            inMenu = false;
        }
        inGame = true;
        gp = new GamePanel(WIDTH, HEIGHT, game);
        scp = new ScorePanel(game);
        add(gp);
        add(scp, BorderLayout.NORTH);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: handles a number being guessed
    private void handleNumberGuessed(int n) {
        if (guesses.size() == 0 && game.getUnmatchedLocationNums().contains(n)) {
            game.guessACard(n);
            guesses.add(n);
        } else if (guesses.size() == 1 && game.getUnmatchedLocationNums().contains(n)) {
            game.guessACard(n);
            guesses.add(n);
            guessAPair();
        }
    }

    // MODIFIES: this
    // EFFECTS: handles a pair of cards being guessed
    private void guessAPair() {
        int n1 = guesses.get(0);
        int n2 = guesses.get(1);
        game.countAnotherGuess();
        if (game.isAMatch(n1, n2)) {
            game.countAnotherMatch();
            game.removeCardFromBoard(n1);
            game.removeCardFromBoard(n2);
        } else {
            game.findCard(n1).unGuessCard();
            game.findCard(n2).unGuessCard();
        }
        guesses.clear();
    }
}
