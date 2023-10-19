package net.ironpulse.armbot.maths;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AngleNormalizationTest {
    @Test
    void absoluteAngleDegreeTest() {
        assertEquals(0, AngleNormalization.getAbsoluteAngleDegree(720));
        assertEquals(20, AngleNormalization.getAbsoluteAngleDegree(20));
        assertEquals(70, AngleNormalization.getAbsoluteAngleDegree(430));
    }
}
