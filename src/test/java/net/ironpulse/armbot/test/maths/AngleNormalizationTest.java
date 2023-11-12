package net.ironpulse.armbot.test.maths;

import net.ironpulse.armbot.maths.AngleNormalization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AngleNormalizationTest {
    @Test
    void absoluteAngleDegreeTest() {
        Assertions.assertEquals(0, AngleNormalization.getAbsoluteAngleDegree(720));
        assertEquals(20, AngleNormalization.getAbsoluteAngleDegree(20));
        assertEquals(70, AngleNormalization.getAbsoluteAngleDegree(430));
    }
}
