package persistence;

import model.MatchingGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    // Method taken from JsonWriterTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testWriterInvalidFile() {
        try {
            MatchingGame mg = new MatchingGame();
            JsonWriter writer = new JsonWriter("./data/n\0tAFileName.json");
            writer.open();
            fail("IOException was supposed to be thrown, but wasn't");
        } catch (IOException e) {
            // expected
        }
    }

    // Method adapted from JsonWriterTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testWriterNewMatchingGame() {
        try {
            MatchingGame mg = new MatchingGame();
            JsonWriter writer = new JsonWriter("./data/testWriterNewMatchingGame.json");
            writer.open();
            writer.write(mg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNewMatchingGame.json");
            mg = reader.read();
            assertEquals(4, mg.getCardAmount());
            assertEquals(new LinkedList<>(Arrays.asList("A", "B")), mg.getCardIdentities());
            assertEquals(4, mg.getUnmatchedCards().size());
            assertEquals(new LinkedList<>(Arrays.asList(1, 2, 3, 4)), mg.getUnmatchedLocationNums());
            assertEquals(0, mg.getMatchedCards().size());
            assertEquals(0, mg.getNumGuesses());
            assertEquals(0, mg.getNumMatches());
        } catch (IOException e) {
            fail("IOException shouldn't have been thrown, but was");
        }
    }
}
