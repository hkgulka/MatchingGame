package ui;

import model.MatchingGame;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// An application for a matching game
public class MatchingGameApp {
    private static final String JSON_STORE = "./data/MatchingGame.json";
    private MatchingGame game;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    boolean inStart;
    boolean inMenu;
    boolean notQuitting;

    // EFFECTS: runs the matching game application and creates a new JSON writer and
    //          JSON reader
    public MatchingGameApp() {
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
        this.input = new Scanner(System.in);
        this.input.useDelimiter("\n");
        this.inStart = true;
        this.inMenu = true;
        this.notQuitting = true;
        runMatchingGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input and starts the game
    private void runMatchingGame() {
        System.out.println("Welcome to the Matching Game!");
        whileInStart();
        playGame();
    }

    // MODIFIES: this
    // EFFECTS: controls the starting screen for the matching game
    private void whileInStart() {
        while (inStart) {
            displayStartingOptions();
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("n")) {
                game = new MatchingGame();
                inStart = false;
                whileInMenu();
            } else {
                try {
                    processCommandFromStart(command);
                } catch (IOException e) {
                    System.out.println("You don't seem to have a previous game saved.");
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: controls the menu screen for the matching game
    private void whileInMenu() {
        while (inMenu) {
            displayMenu();
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("p")) {
                inMenu = false;
            } else {
                processCommandFromMenu(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command at the start
    private void processCommandFromStart(String command) throws IOException {
        if (command.equals("s")) {
            this.inStart = false;
            loadMatchingGame();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command from the menu
    private void processCommandFromMenu(String command) {
        if (command.equals("a")) {
            game.addCardPair();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays option for user to start a new game or load game from file
    private void displayStartingOptions() {
        System.out.println("\nEach card in the game has an 'identity'. Your goal is to guess all");
        System.out.println("of the pairs of cards with matching identities, without seeing those");
        System.out.println("identities all at once. When you guess a card, its identity will be");
        System.out.println("temporarily revealed to you -- try to remember it for later guesses!");
        System.out.println("\nSelect an option below:");
        System.out.println("\tn -> start a new game");
        System.out.println("\ts -> load a previous saved game from file");
    }

    // EFFECTS: displays the menu of options to the user
    private void displayMenu() {
        System.out.println("\nThere are currently " + game.getCardAmount() / 2 + " pairs of cards in the game.");
        System.out.println("Select an option from the menu:");
        System.out.println("\ta -> add a pair of cards");
        System.out.println("\tp -> play the game");
    }

    // MODIFIES: this
    // EFFECTS: loads the matching game from file
    private void loadMatchingGame() throws IOException {
        this.game = jsonReader.read();
        System.out.println("Loading your game from " + JSON_STORE + "...");
    }

    // MODIFIES: this
    // EFFECTS: runs the matching game
    private void playGame() {
        boolean gameNotOver = true;
        System.out.println("\nStarting the game!");

        while (gameNotOver) {
            viewUnmatchedCardsAndStats();
            guessAPair();
            if (game.checkGameOver()) {
                gameNotOver = false;
            }
        }
        System.out.println("\nGame over!");
        System.out.println("You took " + game.getNumGuesses() + " guesses to get " + game.getNumMatches() + "/"
                + game.getNumMatches() + " matches.");
    }

    // EFFECTS: displays the remaining unmatched cards on the board, the number of guesses made,
    //          and the number of matches made
    private void viewUnmatchedCardsAndStats() {
        System.out.println("\nCards remaining:");
        System.out.println(game.getUnmatchedLocationNums());
        System.out.println("\tGuesses: " + game.getNumGuesses());
        System.out.println("\tMatches: " + game.getNumMatches() + "/" + game.getCardAmount() / 2);
        System.out.println("Type 'q' at any point to quit the game.");
    }

    // MODIFIES: this
    // EFFECTS: allows user to make a guess by selecting a pair
    private void guessAPair() {
        String command = null;

        System.out.println("\nMake a guess!");
        System.out.println("Type the first number of the pair.");
        command = input.next();
        quitting(command);
        int number1 = Integer.parseInt(command);
        System.out.println("\tIdentity: " + game.findCard(number1).getIdentity());

        System.out.println("Type the second number of the pair.");
        command = input.next();
        quitting(command);
        int number2 = Integer.parseInt(command);
        System.out.println("\tIdentity: " + game.findCard(number2).getIdentity());
        System.out.println("[hit ENTER to continue]");
        command = input.next();
        if (game.isAMatch(number1, number2)) {
            foundAMatch(number1, number2);
        } else {
            System.out.println("\nSorry, no match here...");
        }
        game.countAnotherGuess();
    }

    // MODIFIES: this
    // EFFECTS: displays text saying the match is correct, removes the matched cards from the board,
    //          and adds another guess to the count
    private void foundAMatch(int number1, int number2) {
        System.out.println("\nCongrats, you found a match!");
        game.removeCardFromBoard(number1);
        game.removeCardFromBoard(number2);
        game.countAnotherMatch();
    }

    // MODIFIES: this
    // EFFECTS: controls the quitting mechanism
    private void quitting(String command) {
        if (command.equals("q")) {
            quittingMenu();
            processQuittingCommand();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays a quitting menu and prompts user to choose an option
    private void quittingMenu() {
        System.out.println("Leaving so soon?");
        System.out.println("\nPlease select an option below:");
        System.out.println("\tq -> quit the game without saving");
        System.out.println("\ts -> save and quit the game");
    }

    // MODIFIES: this
    // EFFECTS: processes user input from the quitting menu
    private void processQuittingCommand() {
        String command = input.next();
        if (command.equals("q")) {
            System.out.println("Bye bye!");
            System.exit(0);
        } else if (command.equals("s")) {
            try {
                this.jsonWriter.open();
                this.jsonWriter.write(this.game);
                this.jsonWriter.close();
                System.out.println("Saved the game to " + JSON_STORE + ".");
                System.out.println("See you later!");
                System.exit(0);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to save to file:" + JSON_STORE);
            }
        } else {
            System.out.println("Selection is not valid...");

        }
    }
}
