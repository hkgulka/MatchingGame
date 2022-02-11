package model;

// Represents a card having an identity, location number, and status (matched/true or unmatched/false)
public class Card {

    private String identity;  // unique identity shared only by 2 cards (a pair)
    private int locationNum;  // the location of the card on the board
    private boolean status;   // is true if card has been matched; false if unmatched

    // EFFECTS: constructs a card with a string as an identity;
    //          initial location number = 0; status is false (unmatched)
    public Card(String identity) {
        this.identity = identity;
        this.locationNum = 0;
        this.status = false;
    }

    // REQUIRES: the status of the card is unmatched (false)
    // MODIFIES: this
    // EFFECTS: changes the status of a card from unmatched to matched
    public void matchCard() {
        this.status = true;
    }

    // REQUIRES: newNum is >0
    // MODIFIES: this
    // EFFECTS: changes the location number of a card to newNum
    public void changeLocationNum(int newNum) {
        this.locationNum = newNum;
    }

    // EFFECTS: returns the identity of the card
    public String getIdentity() {
        return this.identity;
    }

    // EFFECTS: returns the location of the card on the board
    public int getLocationNum() {
        return this.locationNum;
    }

    // EFFECTS: returns true if the card has been matched, false if it has not
    public boolean getStatus() {
        return this.status;
    }
}
