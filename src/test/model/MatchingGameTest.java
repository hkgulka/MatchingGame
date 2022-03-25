package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MatchingGameTest {

    private MatchingGame testGame;

    @BeforeEach
    void runBefore() {
        testGame = new MatchingGame();
    }

    @Test
    void testConstructor() {
        assertEquals(4, testGame.getCardAmount());
        assertEquals(new LinkedList<>(Arrays.asList("A", "B")), testGame.getCardIdentities());
        assertEquals(4, testGame.getUnmatchedCards().size());
        assertEquals(new LinkedList<>(Arrays.asList(1, 2, 3, 4)), testGame.getUnmatchedLocationNums());
        assertEquals(0, testGame.getMatchedCards().size());
        assertEquals(0, testGame.getNumGuesses());
        assertEquals(0, testGame.getNumMatches());
    }

    @Test
    void testAdd1CardPair() {
        testGame.addCardPair();

        assertEquals(6, testGame.getUnmatchedCards().size());
        assertEquals(6, testGame.getCardAmount());
        assertEquals(new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6)), testGame.getUnmatchedLocationNums());
        assertEquals(new LinkedList<>(Arrays.asList("A", "B", "C")), testGame.getCardIdentities());
    }

    @Test
    void testAddCardPairTooManyPairs() {
        testGame.addCardPair();
        testGame.addCardPair();

        assertEquals(8, testGame.getUnmatchedCards().size());
        assertEquals(8, testGame.getCardAmount());
        assertEquals(new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)),
                testGame.getUnmatchedLocationNums());
        assertEquals(new LinkedList<>(Arrays.asList("A", "B", "C", "D")),
                testGame.getCardIdentities());

        testGame.addCardPair();
        assertEquals(8, testGame.getUnmatchedCards().size());
        assertEquals(8, testGame.getCardAmount());
        assertEquals(new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)),
                testGame.getUnmatchedLocationNums());
        assertEquals(new LinkedList<>(Arrays.asList("A", "B", "C", "D")),
                testGame.getCardIdentities());
    }

    @Test
    void testFindUnusedIdentity() {
        assertEquals("C", testGame.findUnusedIdentity());
        testGame.addCardPair();
        assertEquals("D", testGame.findUnusedIdentity());
        testGame.addCardPair();
        assertEquals("E", testGame.findUnusedIdentity());
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
        testGame.addCardPair();
     assertEquals("E", testGame.findUnusedIdentity());
    }

    @Test
    void testAddNewCardIdentity() {
        testGame.addNewCardIdentity("C");
        assertEquals(new LinkedList<>(Arrays.asList("A", "B", "C")), testGame.getCardIdentities());

        testGame.addNewCardIdentity("D");
        assertEquals(new LinkedList<>(Arrays.asList("A", "B", "C", "D")), testGame.getCardIdentities());
    }

    @Test
    void testRemoveCardFromBoard() {
        testGame.removeCardFromBoard(1);

        assertEquals(3, testGame.getUnmatchedCards().size());
        assertEquals(1, testGame.getMatchedCards().size());
        assertEquals(new LinkedList<>(Arrays.asList(2, 3, 4)), testGame.getUnmatchedLocationNums());

        testGame.removeCardFromBoard(3);

        assertEquals(2, testGame.getUnmatchedCards().size());
        assertEquals(2, testGame.getMatchedCards().size());
        assertEquals(new LinkedList<>(Arrays.asList(2, 4)), testGame.getUnmatchedLocationNums());

        testGame.removeCardFromBoard(4);

        assertEquals(1, testGame.getUnmatchedCards().size());
        assertEquals(3, testGame.getMatchedCards().size());
        assertEquals(new LinkedList<>(Arrays.asList(2)), testGame.getUnmatchedLocationNums());
    }

    @Test
    void testFindCard() {
        assertNotNull(testGame.findCard(1));
        assertNotNull(testGame.findCard(2));
        assertNotNull(testGame.findCard(3));
        assertNotNull(testGame.findCard(4));
        assertNull(testGame.findCard(5));
        assertNull(testGame.findCard(0));
    }

    @Test
    void testIsAMatch() {
        assertTrue(testGame.isAMatch(1, 2) ||
                testGame.isAMatch(1, 3) ||
                testGame.isAMatch(1, 4));

        assertTrue(testGame.isAMatch(2, 1) ||
                testGame.isAMatch(2, 3) ||
                testGame.isAMatch(2, 4));

        assertTrue(testGame.isAMatch(3, 1) ||
                testGame.isAMatch(3, 2) ||
                testGame.isAMatch(3, 4));

        assertTrue(testGame.isAMatch(4, 1) ||
                testGame.isAMatch(4, 2) ||
                testGame.isAMatch(4, 3));
    }

    @Test
    void testCountAnotherGuess() {
        testGame.countAnotherGuess();
        assertEquals(1, testGame.getNumGuesses());

        testGame.countAnotherGuess();
        assertEquals(2, testGame.getNumGuesses());
    }

    @Test
    void testCountAnotherMatch() {
        testGame.countAnotherMatch();
        assertEquals(1, testGame.getNumMatches());

        testGame.countAnotherMatch();
        assertEquals(2, testGame.getNumMatches());
    }

    @Test
    void testMakeBoardArrangementRange() {
        testGame.makeBoardArrangement();
        assertTrue(1 <= testGame.getUnmatchedCards().get(0).getLocationNum());
        assertTrue(testGame.getUnmatchedCards().get(0).getLocationNum() <= 4);
        assertTrue(1 <= testGame.getUnmatchedCards().get(1).getLocationNum());
        assertTrue(testGame.getUnmatchedCards().get(1).getLocationNum() <= 4);
        assertTrue(1 <= testGame.getUnmatchedCards().get(2).getLocationNum());
        assertTrue(testGame.getUnmatchedCards().get(2).getLocationNum() <= 4);
        assertTrue(1 <= testGame.getUnmatchedCards().get(3).getLocationNum());
        assertTrue(testGame.getUnmatchedCards().get(3).getLocationNum() <= 4);
    }

    @Test
    void testMakeBoardArrangementUniqueValues() {
        testGame.makeBoardArrangement();
        assertNotEquals(testGame.getUnmatchedCards().get(0).getLocationNum(),
                testGame.getUnmatchedCards().get(1).getLocationNum());
        assertNotEquals(testGame.getUnmatchedCards().get(0).getLocationNum(),
                testGame.getUnmatchedCards().get(2).getLocationNum());
        assertNotEquals(testGame.getUnmatchedCards().get(0).getLocationNum(),
                testGame.getUnmatchedCards().get(3).getLocationNum());
        assertNotEquals(testGame.getUnmatchedCards().get(1).getLocationNum(),
                testGame.getUnmatchedCards().get(2).getLocationNum());
        assertNotEquals(testGame.getUnmatchedCards().get(1).getLocationNum(),
                testGame.getUnmatchedCards().get(2).getLocationNum());
        assertNotEquals(testGame.getUnmatchedCards().get(2).getLocationNum(),
                testGame.getUnmatchedCards().get(3).getLocationNum());
    }

    @Test
    void testCheckGameOver() {
        assertFalse(testGame.checkGameOver());
        testGame.countAnotherMatch();
        assertFalse(testGame.checkGameOver());
        testGame.countAnotherMatch();
        assertTrue(testGame.checkGameOver());
    }

    @Test
    void testGuessACard() {
        assertFalse(testGame.findCard(1).isBeingGuessed());
        testGame.guessACard(1);
        assertTrue(testGame.findCard(1).isBeingGuessed());

        assertFalse(testGame.findCard(4).isBeingGuessed());
        testGame.guessACard(4);
        assertTrue(testGame.findCard(4).isBeingGuessed());

        testGame.guessACard(5);
        assertTrue(testGame.findCard(1).isBeingGuessed());
        assertFalse(testGame.findCard(2).isBeingGuessed());
        assertFalse(testGame.findCard(3).isBeingGuessed());
        assertTrue(testGame.findCard(4).isBeingGuessed());
    }
}