package net.ironpulse.armbot.maths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RangeTest {

    @Test
    void inRangeTest() {
        var range = new Range(1, 20);
        assertTrue(range.inRange(5));
        assertTrue(range.inRange(20));
        assertFalse(range.inRange(0));
    }

    @Test
    void clampTest() {
        var range = new Range(1, 20);
        assertEquals(5, range.clamp(5));
        assertEquals(20, range.clamp(30));
        assertEquals(1, range.clamp(-1));
    }

    @Test
    void averageTest() {
        var rangeOdd = new Range(1, 20);
        assertEquals(10.5, rangeOdd.average());
        var rangeEven = new Range(1, 19);
        assertEquals(10, rangeEven.average());
    }
}
