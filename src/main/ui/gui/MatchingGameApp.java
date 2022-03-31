package ui.gui;

import model.Event;
import model.EventLog;
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
import java.util.concurrent.*;

// Represents a matching game application with a graphical user interface
public class MatchingGameApp extends JFrame implements ActionListener {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private MatchingGame game;
    private StartPanel stp;
    private MenuPanel mp;
    private GamePanel gp;
    private ScorePanel scp;
    private GameOverPanel gop;
    private QuittingPopUp qp;
    private Popup popup;

    private static final String JSON_STORE = "./data/MatchingGame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private List<Integer> guesses;
    private ScheduledExecutorService schedule;

    // EFFECTS: constructs a matching game app with a starting window
    public MatchingGameApp() {
        super("Matching Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        game = new MatchingGame();
        stp = new StartPanel(WIDTH, HEIGHT, this);
        mp = new MenuPanel(WIDTH, HEIGHT, game, this);
        gp = new GamePanel(WIDTH, HEIGHT, game, this);
        scp = new ScorePanel(game);
        gop = new GameOverPanel(WIDTH, HEIGHT, game);
        qp = new QuittingPopUp(this);
        PopupFactory pf = new PopupFactory();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        popup = pf.getPopup(this, qp, (screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        add(stp);
        pack();
        centreOnScreen();
        setVisible(true);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        guesses = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: centres the window on the desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // MODIFIES: this
    // EFFECTS: responds to each specific ActionEvent
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("new game")) {
            newGame();
        } else if (e.getActionCommand().equals("load game")) {
            loadGameFromSave();
        } else if (e.getActionCommand().equals("add cards")) {
            game.addCardPair();
            mp.updateText();
        } else if (e.getActionCommand().equals("play game")) {
            playGame();
        } else if (e.getActionCommand().equals("leave game")) {
            quitGame();
        } else if (e.getActionCommand().equals("quit")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("save and quit")) {
            saveAndQuit();
        } else if (e.getActionCommand().equals("cancel")) {
            returnToGame();
        } else {
            int i = Integer.parseInt(e.getActionCommand());
            handleNumberGuessed(i);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the current state of the game to JSON_STORE
    private void saveAndQuit() {
        try {
            this.jsonWriter.open();
            this.jsonWriter.write(this.game);
            this.jsonWriter.close();
            System.exit(0);
        } catch (FileNotFoundException f) {
            // does nothing (no file to save to)
        }
    }

    // MODIFIES: this
    // EFFECTS: plays the new matching game and advances beyond the starting screen
    private void newGame() {
        remove(stp);
        add(mp);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: loads the game from the save file and plays it
    private void loadGameFromSave() {
        try {
            this.game = jsonReader.read();
            remove(stp);
            playGame();
        } catch (IOException io) {
            // does nothing (no file saved)
        }
    }

    // MODIFIES: this
    // EFFECTS: plays the matching game by adding the game and score panels
    private void playGame() {
        remove(mp);
        gp = new GamePanel(WIDTH, HEIGHT, game, this);
        scp = new ScorePanel(game);
        add(gp);
        add(scp, BorderLayout.NORTH);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: handles a card location number being guessed
    private void handleNumberGuessed(int n) {
        if (guesses.size() == 0) {
            guesses.add(n);
            gp.showCardIdentity(n);
        } else if (guesses.size() == 1 && n != guesses.get(0)) {
            guesses.add(n);
            gp.showCardIdentity(n);
            schedule = Executors.newSingleThreadScheduledExecutor();
            schedule.schedule(this::guessAPair, 1, TimeUnit.SECONDS);
        }
    }

    // MODIFIES: this
    // EFFECTS: handles a pair of card location numbers being guessed
    public void guessAPair() {
        int n1 = guesses.get(0);
        int n2 = guesses.get(1);
        game.countAnotherGuess();
        if (game.isAMatch(n1, n2)) {
            game.countAnotherMatch();
            gp.removeCardFromPlay(n1);
            gp.removeCardFromPlay(n2);
            game.removeCardFromBoard(n1);
            game.removeCardFromBoard(n2);
        } else {
            gp.hideCardIdentity(n1);
            gp.hideCardIdentity(n2);
        }
        scp.update();
        guesses.clear();
        if (game.checkGameOver()) {
            schedule.schedule(this::gameOver, 1, TimeUnit.SECONDS);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays an end-game panel with game stats
    private void gameOver() {
        remove(gp);
        remove(scp);
        gop = new GameOverPanel(WIDTH, HEIGHT, game);
        add(gop);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: displays a popup panel with options for quitting the game, saving and quitting, and
    //          returning to the game
    private void quitGame() {
        PopupFactory pf = new PopupFactory();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        popup = pf.getPopup(this, qp, (screen.width - 350) / 2,
                (screen.height - 200) / 2);
        popup.show();
    }

    // MODIFIES: this
    // EFFECTS: hides the popup window displaying quitting options
    private void returnToGame() {
        popup.hide();
    }

}
