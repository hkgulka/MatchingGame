package persistence;

import model.MatchingGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Class taken and adapted from JsonReaderTest class in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest {

    // Method taken from JsonReaderTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderNonExistantFile() {
        JsonReader reader = new JsonReader("./data/noFIleExists.json");
        try {
            MatchingGame mg = reader.read();
            fail("IOException was expected to be thrown, but was not");
        } catch (IOException e) {
            // expected
        }
    }

    // Method adapted from JsonReaderTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderNewMatchingGame() {
        JsonReader reader = new JsonReader("./data/testReaderNewMatchingGame.json");
        try {
            MatchingGame mg = reader.read();
            assertEquals(4, mg.getCardAmount());
            assertEquals(new LinkedList<>(Arrays.asList("A", "B")), mg.getCardIdentities());
            assertEquals(4, mg.getUnmatchedCards().size());
            assertEquals(new LinkedList<>(Arrays.asList(1, 2, 3, 4)), mg.getUnmatchedLocationNums());
            assertEquals(0, mg.getMatchedCards().size());
            assertEquals(0, mg.getNumGuesses());
            assertEquals(0, mg.getNumMatches());
        } catch (IOException e) {
            fail("Couldn't read the matching game from file");
        }
    }

    // Method adapted from JsonReaderTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderPartlyDoneMatchingGame() {
        JsonReader reader = new JsonReader("./data/testReaderPartlyDoneMatchingGame.json");
        try {
            MatchingGame mg = reader.read();
            assertEquals(6, mg.getCardAmount());
            assertEquals(new LinkedList<>(Arrays.asList("A", "B", "C")), mg.getCardIdentities());
            assertEquals(2, mg.getUnmatchedCards().size());
            assertEquals(new LinkedList<>(Arrays.asList(1, 5)), mg.getUnmatchedLocationNums());
            assertEquals(4, mg.getMatchedCards().size());
            assertEquals(5, mg.getNumGuesses());
            assertEquals(2, mg.getNumMatches());
        } catch (IOException e) {
            fail("Couldn't read the matching game from file");
        }
    }
}
