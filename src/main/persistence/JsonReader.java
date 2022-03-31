package persistence;

import model.Card;
import model.MatchingGame;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Collections;

// A reader that reads a matching game from JSON data stored in a file
public class JsonReader {
    private String source;

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads the matching game from file and returns it; throws IOException
    //          if an error occurs while reading data from file
    public MatchingGame read() throws IOException {
        String jsonData = readFile(this.source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMatchingGame(jsonObject);
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads the source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> stringBuilder.append(s));
        }
        return stringBuilder.toString();
    }

    // EFFECTS: parses a matching game from a JSON object and returns it
    private MatchingGame parseMatchingGame(JSONObject jsonObject) {
        int cardAmount = jsonObject.getInt("card amount");
        List<String> cardIdentities = parseStringList(jsonObject.getJSONArray("card identities"));
        List<Card> unmatchedCards = parseCardList(jsonObject.getJSONArray("unmatched cards"));
        LinkedList<Integer> unmatchedLocationNums = parseNumList(jsonObject.getJSONArray(
                "unmatched location numbers"));
        List<Card> matchedCards = parseCardList(jsonObject.getJSONArray("matched cards"));
        int numGuesses = jsonObject.getInt("number of guesses");
        int numMatches = jsonObject.getInt("number of matches");
        return new MatchingGame(cardAmount, cardIdentities, unmatchedCards, unmatchedLocationNums,
                matchedCards, numGuesses, numMatches);
    }


    // EFFECTS: obtains a list of cards from a JSON object
    private List<Card> parseCardList(JSONArray jsonArray) {
        List<Card> cardList = new LinkedList<>();
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            cardList.add(parseCard(nextCard));
        }
        Collections.reverse(cardList);
        return cardList;
    }

    // EFFECTS: obtains a linked list of integers from a JSON object
    private LinkedList<Integer> parseNumList(JSONArray jsonArray) {
        LinkedList<Integer> numList = new LinkedList<>();
        for (int i = jsonArray.length() - 1; i >= 0; i--) {
            int num = jsonArray.getInt(i);
            numList.add(num);
        }
        Collections.reverse(numList);
        return numList;
    }

    // EFFECTS: obtains a list of strings from a JSON object
    private List<String> parseStringList(JSONArray jsonArray) {
        List<String> stringList = new LinkedList<>();
        for (int i = jsonArray.length() - 1; i >= 0; i--) {
            String string = jsonArray.getString(i);
            stringList.add(string);
        }
        Collections.reverse(stringList);
        return stringList;
    }

    // EFFECTS: obtains a card from a JSON object
    private Card parseCard(JSONObject jsonObject) {
        String identity = jsonObject.getString("identity");
        int locationNum = jsonObject.getInt("location number");
        boolean status = jsonObject.getBoolean("status");
        return new Card(identity, locationNum, status);
    }
}
