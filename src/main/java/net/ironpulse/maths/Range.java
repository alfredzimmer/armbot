package net.ironpulse.maths;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Range {
    private final double min;
    private final double max;

    public boolean inRange(double value) {
        return value >= min && value <= max;
    }

    public static boolean inRange(double value, double min, double max) {
        return new Range(min, max).inRange(value);
    }

    public double clamp(double value) {
        return Math.max(min, Math.min(value, max));
    }

    public static double clamp(double value, double min, double max) {
        return new Range(min, max).clamp(value);
    }

    public double average() {
        return (min + max) / 2;
    }
}
