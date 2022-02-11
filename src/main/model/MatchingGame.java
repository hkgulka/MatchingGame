package model;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// Represents a matching game with an amount of cards, list of card identities in use,
// cards on the board, cards matched, and number of guesses made
public class MatchingGame {
    private static final List<String> POSSIBLE_IDENTITIES = new LinkedList<String>(Arrays.asList("A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
            "Z"));

    private int cardAmount;               // total number of cards in the game
    private List<String> cardIdentities;  // list of all card identities already in the game
    private List<Card> unmatchedCards;    // list of all cards still on the board
    private List<Card> matchedCards;      // list of all cards matched
    private int numGuesses;               // number of guesses made
    private String unusedIdentity;        // the first available identity in POSSIBLE_IDENTITIES; "" if none
    private LinkedList<Integer> locationNumList;// holds a list of remaining possible location numbers

    // EFFECTS: constructs a game with 4 cards, a list of card identities in use,
    //          a list of all cards on the board, empty list of cards matched, and
    //          0 guesses made, then rearranges the board
    public MatchingGame() {
        this.cardAmount = 4;
        this.cardIdentities = new LinkedList<String>(Arrays.asList("A", "B"));
        this.unmatchedCards = new LinkedList<Card>(Arrays.asList(new Card("A"), new Card("A"),
                new Card("B"), new Card("B")));
        this.matchedCards = new LinkedList<>();
        this.numGuesses = 0;
        makeBoardArrangement();
    }

    // REQUIRES: cardAmount / 2 + 1 <= the number of items in POSSIBLE_IDENTITIES
    // MODIFIES: this
    // EFFECTS: adds a new (matching) pair of cards to the board and assigns
    //          them a unique identity, marks that identity as used, then rearranges the board
    public void addCardPair() {
        this.unusedIdentity = findUnusedIdentity();
        this.unmatchedCards.add(new Card(this.unusedIdentity));
        this.unmatchedCards.add(new Card(this.unusedIdentity));
        this.cardAmount = this.cardAmount + 2;
        addNewCardIdentity(this.unusedIdentity);
        makeBoardArrangement();
    }

    // EFFECTS: finds the first identity in POSSIBLE_IDENTITIES that is not in cardIdentities; returns ""
    //          if all identities are in use
    public String findUnusedIdentity() {
        for (String s : POSSIBLE_IDENTITIES) {
            if (cardIdentities.indexOf(s) == -1) {
                return s;
            }
        }
        return "";
    }

    // REQUIRES: this.cardIdentities does not already contain newIdentity AND newIdentity
    //           is in POSSIBLE_IDENTITIES
    // MODIFIES: this
    // EFFECTS: adds a card identity to the list of identities in the game
    public void addNewCardIdentity(String newIdentity) {
        this.cardIdentities.add(newIdentity);
    }

    // REQUIRES: locationNum is within [1, cardAmount] AND locationNum corresponds
    //           to a card in unmatchedCards
    // MODIFIES: this
    // EFFECTS: removes a card from the list of cards on the board and puts it at
    //          the end of the list of matched cards, also changes its status to matched
    public void removeCardFromBoard(int locationNum) {
        this.unmatchedCards.get(findCard(locationNum)).matchCard();
        this.matchedCards.add(this.unmatchedCards.get(findCard(locationNum)));
        this.unmatchedCards.remove(findCard(locationNum));
    }

    // REQUIRES: locationNum is within [1, cardAmount]
    // EFFECTS: returns the index of the card in unmatchedCards with the given locationNum,
    //          or returns -1 if no card with that locationNum is found
    public int findCard(int locationNum) {
        for (Card c : this.unmatchedCards) {
            if (c.getLocationNum() == locationNum) {
                return this.unmatchedCards.indexOf(c);
            }
        }
        return -1;
    }

    // REQUIRES: both cards are in unmatchedCards
    // EFFECTS: returns true if two cards have the same identity, false otherwise
    public boolean isAMatch(Card c1, Card c2) {
        return c1.getIdentity().equals(c2.getIdentity());
    }

    // MODIFIES: this
    // EFFECTS: adds 1 to the number of guesses made
    public void countAnotherGuess() {
        this.numGuesses = this.numGuesses + 1;
    }

    // MODIFIES: this
    // EFFECTS: randomly assigns a (different) locationNum to each card in
    //          the list of unmatched cards
    public void makeBoardArrangement() {
        this.locationNumList = new LinkedList<>();
        for (int i = this.unmatchedCards.size(); i >= 1; --i) {
            this.locationNumList.add(i);
        }
        Collections.shuffle(this.locationNumList);
        for (Card c : this.unmatchedCards) {
            c.changeLocationNum(this.locationNumList.removeFirst());
        }
    }

    // EFFECTS: returns the total number of cards in the game
    public int getCardAmount() {
        return this.cardAmount;
    }

    // EFFECTS: returns the list of card identities already in the game
    public List<String> getCardIdentities() {
        return this.cardIdentities;
    }

    // EFFECTS: returns the list of cards still on the board
    public List<Card> getUnmatchedCards() {
        return unmatchedCards;
    }

    // EFFECTS: returns the list of cards that have been matched
    //          and removed from the board
    public List<Card> getMatchedCards() {
        return matchedCards;
    }

    // EFFECTS: returns the number of match guesses made so far
    public int getNumGuesses() {
        return this.numGuesses;
    }
}
