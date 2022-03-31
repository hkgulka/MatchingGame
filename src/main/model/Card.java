package model;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import persistence.Writable;

// Represents a card having an identity, location number, and status (matched/true or unmatched/false)
public class Card implements Writable {

    private String identity;  // unique identity shared only by 2 cards (a pair)
    private int locationNum;  // the location of the card on the board
    private boolean status;   // is true if card has been matched; false if unmatched

    // EFFECTS: constructs a card with a string as an identity;
    //          initial location number = 0; status is false (unmatched) and card is not being guessed
    public Card(String identity) {
        this.identity = identity;
        this.locationNum = 0;
        this.status = false;
    }

    // EFFECTS: constructs a card with an identity, a location number, and a status
    public Card(String identity, int locationNum, boolean status) {
        this.identity = identity;
        this.locationNum = locationNum;
        this.status = status;
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

    // Method taken and adapted from Thingy class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("identity", this.identity);
        json.put("location number", this.locationNum);
        json.put("status", this.status);
        return json;
    }
}
