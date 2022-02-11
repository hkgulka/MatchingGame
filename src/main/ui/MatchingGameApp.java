package ui;

import model.MatchingGame;

import java.util.Scanner;

// An application for a matching game
public class MatchingGameApp {

    private MatchingGame game;
    private Scanner input;

    // EFFECTS: runs the matching game application
    public MatchingGameApp() {
        runMatchingGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input and starts the game
    private void runMatchingGame() {
        boolean notYetStarted = true;
        String command = null;

        initialize();

        System.out.println("Welcome to the Matching Game!");
        while (notYetStarted) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("p")) {
                notYetStarted = false;
            } else {
                processCommand(command);
            }
        }
        playGame();
        System.out.println("\nGame over!");
        System.out.println("You took " + game.getNumGuesses() + " guesses to get " + game.getNumMatches() + "/"
                + game.getNumMatches() + " matches.");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            game.addCardPair();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the game
    private void initialize() {
        game = new MatchingGame();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays the menu of options to the user
    private void displayMenu() {
        System.out.println("\nThere are currently " + game.getCardAmount() / 2 + " pairs of cards in the game.");
        System.out.println("Menu:");
        System.out.println("\ta -> add a pair of cards");
        System.out.println("\tp -> play the game");
        System.out.println("Type your selection below:");
    }

    // MODIFIES: this
    // EFFECTS: runs the matching game
    private void playGame() {
        boolean gameNotOver = true;
        String command = null;
        System.out.println("\nStarting the game!");

        while (gameNotOver) {
            viewUnmatchedCardsAndStats();
            guessAPair();
            if (game.checkGameOver()) {
                gameNotOver = false;
            }
        }
    }

    // EFFECTS: displays the remaining unmatched cards on the board, the number of guesses made,
    //          and the number of matches made
    private void viewUnmatchedCardsAndStats() {
        System.out.println("\nCards remaining:");
        System.out.println(game.getUnmatchedLocationNums());
        System.out.println("\tGuesses: " + game.getNumGuesses());
        System.out.println("\tMatches: " + game.getNumMatches() + "/" + game.getCardAmount() / 2);
    }

    // MODIFIES: this
    // EFFECTS: allows user to make a guess by selecting a pair
    private void guessAPair() {
        String command = null;

        System.out.println("\nMake a guess!");
        System.out.println("Type the first number of the pair.");
        command = input.next();
        int number1 = Integer.parseInt(command);
        System.out.println("\tIdentity: " + game.findCard(number1).getIdentity());

        System.out.println("Type the second number of the pair.");
        command = input.next();
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
}
