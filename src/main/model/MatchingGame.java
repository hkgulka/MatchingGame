package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// Represents a matching game with an amount of cards, list of card identities in use,
// cards on the board, cards matched, and number of guesses made
public class MatchingGame implements Writable {
    private static final List<String> POSSIBLE_IDENTITIES = new LinkedList<>(Arrays.asList("A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
            "Z"));

    private int cardAmount;                            // total number of cards in the game
    private List<String> cardIdentities;               // list of all card identities already in the game
    private List<Card> unmatchedCards;                 // list of all cards still on the board
    private LinkedList<Integer> unmatchedLocationNums;       // list of all the location numbers of unmatched cards
    private List<Card> matchedCards;                   // list of all cards matched
    private int numGuesses;                            // number of guesses made
    private int numMatches;                            // number of matches made

    // EFFECTS: constructs a game with 4 cards, a list of card identities in use,
    //          a list of all cards on the board, a list of the location numbers on the board,
    //          an empty list of cards matched, 0 guesses made, and 0 matches made, then rearranges the board
    public MatchingGame() {
        this.cardAmount = 4;
        this.cardIdentities = new LinkedList<>(Arrays.asList("A", "B"));
        this.unmatchedCards = new LinkedList<>(Arrays.asList(new Card("A"), new Card("A"),
                new Card("B"), new Card("B")));
        this.unmatchedLocationNums = new LinkedList<>(Arrays.asList(1, 2, 3, 4));
        this.matchedCards = new LinkedList<>();
        this.numGuesses = 0;
        this.numMatches = 0;
        makeBoardArrangement();
    }

    // EFFECTS: constructs a game with a given number of cards, a list of card identities in use,
    //          a list of all cards on the board, a list of the location numbers on the board (already arranged),
    //          a list of cards matched, number of guesses made, and number of matches made
    public MatchingGame(int cardAmount, List<String> cardIdentities, List<Card> unmatchedCards,
                        LinkedList<Integer> unmatchedLocationNums, List<Card> matchedCards, int numGuesses,
                        int numMatches) {
        this.cardAmount = cardAmount;
        this.cardIdentities = cardIdentities;
        this.unmatchedCards = unmatchedCards;
        this.unmatchedLocationNums = unmatchedLocationNums;
        this.matchedCards = matchedCards;
        this.numGuesses = numGuesses;
        this.numMatches = numMatches;
    }

    // REQUIRES: cardAmount / 2 + 1 <= the number of items in POSSIBLE_IDENTITIES
    // MODIFIES: this
    // EFFECTS: adds a new (matching) pair of cards to the board and assigns
    //          them a unique identity, marks that identity as used, makes a new list of location numbers
    //          for cards on the board, then rearranges the board
    public void addCardPair() {
        String unusedIdentity = findUnusedIdentity();
        this.unmatchedCards.add(new Card(unusedIdentity));
        this.unmatchedCards.add(new Card(unusedIdentity));
        this.cardAmount = this.cardAmount + 2;
        this.unmatchedLocationNums = new LinkedList<>();
        for (int i = 1; i <= this.unmatchedCards.size(); ++i) {
            this.unmatchedLocationNums.add(i);
        }
        addNewCardIdentity(unusedIdentity);
        makeBoardArrangement();
    }

    // EFFECTS: finds the first identity in POSSIBLE_IDENTITIES that is not in cardIdentities; returns null
    //          if all identities are in use
    public String findUnusedIdentity() {
        for (String s : POSSIBLE_IDENTITIES) {
            if (!cardIdentities.contains(s)) {
                return s;
            }
        }
        return null;
    }

    // REQUIRES: this.cardIdentities does not already contain newIdentity AND newIdentity
    //           is in POSSIBLE_IDENTITIES
    // MODIFIES: this
    // EFFECTS: adds a card identity to the list of identities in the game
    public void addNewCardIdentity(String newIdentity) {
        this.cardIdentities.add(newIdentity);
    }

    // REQUIRES: locationNum corresponds to a card in unmatchedCards
    // MODIFIES: this
    // EFFECTS: removes a card from the list of cards on the board and puts it at
    //          the end of the list of matched cards, removes that location number
    //          from the list of unmatched location numbers, and changes the card's status to matched
    public void removeCardFromBoard(int locationNum) {
        findCard(locationNum).matchCard();
        this.matchedCards.add(findCard(locationNum));
        this.unmatchedCards.remove(findCard(locationNum));
        this.unmatchedLocationNums.removeFirstOccurrence(locationNum);
    }

    // REQUIRES: locationNum is within [1, cardAmount]
    // EFFECTS: returns the card in unmatchedCards with the given locationNum,
    //          or returns null if no card with that locationNum is found
    public Card findCard(int locationNum) {
        for (Card c : this.unmatchedCards) {
            if (c.getLocationNum() == locationNum) {
                return c;
            }
        }
        return null;
    }

    // REQUIRES: both locationNum1 and locationNum2 correspond to a card in unmatchedCards
    // EFFECTS: returns true if two cards of given location numbers have the same identity, false otherwise
    public boolean isAMatch(int locationNum1, int locationNum2) {
        Card c1 = findCard(locationNum1);
        Card c2 = findCard(locationNum2);
        return c1.getIdentity().equals(c2.getIdentity());
    }

    // MODIFIES: this
    // EFFECTS: guesses the card with the location number n
    public void guessACard(int n) {
        if (findCard(n) != null) {
            findCard(n).guessCard();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds 1 to the number of guesses made
    public void countAnotherGuess() {
        this.numGuesses = this.numGuesses + 1;
    }

    // MODIFIES: this
    // EFFECTS: adds 1 to the number of matches made
    public void countAnotherMatch() {
        this.numMatches = this.numMatches + 1;
    }

    // MODIFIES: this
    // EFFECTS: randomly assigns a (different) locationNum to each card in
    //          the list of unmatched cards
    public void makeBoardArrangement() {
        LinkedList<Integer> locationNumList = new LinkedList<>();
        for (int i = this.unmatchedCards.size(); i >= 1; --i) {
            locationNumList.add(i);
        }
        Collections.shuffle(locationNumList);
        for (Card c : this.unmatchedCards) {
            c.changeLocationNum(locationNumList.removeFirst());
        }
    }

    // MODIFIES: this
    // EFFECTS: checks if all matches have been made, and if so produces true; else false
    public boolean checkGameOver() {
        int totalMatches = this.cardAmount / 2;
        return totalMatches == this.numMatches;
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
        return this.unmatchedCards;
    }

    public List<Integer> getUnmatchedLocationNums() {
        return this.unmatchedLocationNums;
    }

    // EFFECTS: returns the list of cards that have been matched
    //          and removed from the board
    public List<Card> getMatchedCards() {
        return this.matchedCards;
    }

    // EFFECTS: returns the number of match guesses made so far
    public int getNumGuesses() {
        return this.numGuesses;
    }

    // EFFECTS: returns the number of successful matches made so far
    public int getNumMatches() {
        return this.numMatches;
    }

    // Method taken and adapted from WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("card amount", this.cardAmount);
        json.put("card identities", stringsToJsonArray(this.cardIdentities));
        json.put("unmatched cards", cardsToJsonArray(this.unmatchedCards));
        json.put("unmatched location numbers", integersToJsonArray(this.unmatchedLocationNums));
        json.put("matched cards", cardsToJsonArray(this.matchedCards));
        json.put("number of guesses", this.numGuesses);
        json.put("number of matches", this.numMatches);
        return json;
    }

    // Method taken and adapted from WorkRoom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns a list of cards as a JSON array
    private JSONArray cardsToJsonArray(List<Card> cards) {
        JSONArray jsonArray = new JSONArray();
        for (Card c : cards) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns a list of integers as a JSON array
    private JSONArray integersToJsonArray(List<Integer> integers) {
        return new JSONArray(integers);
    }

    // EFFECTS: returns a list of strings as a JSON array
    private JSONArray stringsToJsonArray(List<String> strings) {
        return new JSONArray(strings);
    }
}
