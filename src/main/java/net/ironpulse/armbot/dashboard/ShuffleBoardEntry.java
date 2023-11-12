package net.ironpulse.armbot.dashboard;

import edu.wpi.first.networktables.GenericEntry;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@Data
@NoArgsConstructor
public class ShuffleBoardEntry<T> {
    private String entryName;

    private Supplier<T> entryValue;

    private GenericEntry entry;

    public static <T> ShuffleBoardEntry<T> builder() {
        return new ShuffleBoardEntry<>();
    }

    public ShuffleBoardEntry<T> entryName(String entryName) {
        this.entryName = entryName;
        return this;
    }

    public ShuffleBoardEntry<T> entryValue(Supplier<T> entryValue) {
        this.entryValue = entryValue;
        return this;
    }

    public ShuffleBoardEntry<T> entry(GenericEntry entry) {
        this.entry = entry;
        return this;
    }

    public ShuffleBoardEntry<T> build() {
        return this;
    }
}
