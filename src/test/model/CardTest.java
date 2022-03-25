package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    private Card testCard;

    @BeforeEach
    void runBefore() {
        testCard = new Card("A");
    }

    @Test
    void testConstructor() {
        assertEquals("A", testCard.getIdentity());
        assertEquals(0, testCard.getLocationNum());
        assertFalse(testCard.getStatus());
        assertFalse(testCard.isBeingGuessed());
    }

    @Test
    void testMatchCard() {
        testCard.matchCard();
        assertTrue(testCard.getStatus());
    }

    @Test
    void testChangeLocationNum() {
        testCard.changeLocationNum(10);
        assertEquals(10, testCard.getLocationNum());

        testCard.changeLocationNum(1);
        assertEquals(1, testCard.getLocationNum());
    }

    @Test
    void testGuessCard() {
        testCard.guessCard();
        assertTrue(testCard.isBeingGuessed());

        testCard.unGuessCard();
        assertFalse(testCard.isBeingGuessed());
    }
}
