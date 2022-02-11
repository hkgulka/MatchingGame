package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchingGameTest {

    private MatchingGame testGame;
    private Card cardA1;
    private Card cardA2;
    private Card cardB1;
    private Card cardB2;
    private Card cardC1;
    private Card cardC2;

    private List<String> actualCardIdentities;


    @BeforeEach
    void runBefore() {
        testGame = new MatchingGame();
        cardA1 = new Card("A");
        cardA2 = new Card("A");
        cardB1 = new Card("B");
        cardB2 = new Card("B");
        cardC1 = new Card("C");
        cardC2 = new Card("C");

        actualCardIdentities = new LinkedList<String>(Arrays.asList("A", "B"));
    }

    @Test
    void testConstructor() {
        assertEquals(4, testGame.getCardAmount());
        assertEquals(actualCardIdentities, testGame.getCardIdentities());
        assertEquals(4, testGame.getUnmatchedCards().size());
        assertEquals(0, testGame.getMatchedCards().size());
        assertEquals(0, testGame.getNumGuesses());
    }

    @Test
    void testAdd1CardPair() {
        testGame.addCardPair();
        actualCardIdentities.add("C");

        assertEquals(6, testGame.getCardAmount());
        assertEquals(actualCardIdentities, testGame.getCardIdentities());
        assertEquals(6, testGame.getUnmatchedCards().size());
    }

    @Test
    void testAdd3CardPair() {
        testGame.addCardPair();
        actualCardIdentities.add("C");
        testGame.addCardPair();
        actualCardIdentities.add("D");
        testGame.addCardPair();
        actualCardIdentities.add("E");

        assertEquals(10, testGame.getCardAmount());
        assertEquals(actualCardIdentities, testGame.getCardIdentities());
        assertEquals(10, testGame.getUnmatchedCards().size());
    }

    @Test
    void testFindUnusedIdentity() {
        assertEquals("C", testGame.findUnusedIdentity());
        testGame.addCardPair();
        assertEquals("D", testGame.findUnusedIdentity());
    }

    @Test
    void testRemoveCardFromBoard() {
        testGame.removeCardFromBoard(1);

        assertEquals(3, testGame.getUnmatchedCards().size());
        assertEquals(1, testGame.getMatchedCards().size());

        testGame.removeCardFromBoard(3);

        assertEquals(2, testGame.getUnmatchedCards().size());
        assertEquals(2, testGame.getMatchedCards().size());

        testGame.removeCardFromBoard(4);

        assertEquals(1, testGame.getUnmatchedCards().size());
        assertEquals(3, testGame.getMatchedCards().size());
    }

    @Test
    void testFindCard() {
        assertNotEquals(-1, testGame.findCard(1));
        assertNotEquals(-1, testGame.findCard(2));
        assertNotEquals(-1, testGame.findCard(3));
        assertNotEquals(-1, testGame.findCard(4));
        assertEquals(-1, testGame.findCard(5));
        assertEquals(-1, testGame.findCard(0));
    }

    @Test
    void testIsAMatch() {
        assertTrue(testGame.isAMatch(cardA1, cardA2));
        assertTrue(testGame.isAMatch(cardC1, cardC2));

        assertFalse(testGame.isAMatch(cardA1, cardB2));
        assertFalse(testGame.isAMatch(cardC2, cardB1));
        assertFalse(testGame.isAMatch(cardA2, cardC1));
    }

    @Test
    void testAddNewCardIdentity() {
        testGame.addNewCardIdentity("C");
        actualCardIdentities.add("C");
        assertEquals(actualCardIdentities, testGame.getCardIdentities());

        testGame.addNewCardIdentity("D");
        actualCardIdentities.add("D");
        assertEquals(actualCardIdentities, testGame.getCardIdentities());
    }

    @Test
    void testCountAnotherGuess() {
        testGame.countAnotherGuess();
        assertEquals(1, testGame.getNumGuesses());

        testGame.countAnotherGuess();
        assertEquals(2, testGame.getNumGuesses());
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
        assertFalse(testGame.getUnmatchedCards().get(0).getLocationNum() ==
                testGame.getUnmatchedCards().get(1).getLocationNum());
        assertFalse(testGame.getUnmatchedCards().get(0).getLocationNum() ==
                testGame.getUnmatchedCards().get(2).getLocationNum());
        assertFalse(testGame.getUnmatchedCards().get(0).getLocationNum() ==
                testGame.getUnmatchedCards().get(3).getLocationNum());
        assertFalse(testGame.getUnmatchedCards().get(1).getLocationNum() ==
                testGame.getUnmatchedCards().get(2).getLocationNum());
        assertFalse(testGame.getUnmatchedCards().get(1).getLocationNum() ==
                testGame.getUnmatchedCards().get(2).getLocationNum());
        assertFalse(testGame.getUnmatchedCards().get(2).getLocationNum() ==
                testGame.getUnmatchedCards().get(3).getLocationNum());
    }
}