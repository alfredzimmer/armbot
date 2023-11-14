package net.ironpulse.armbot.dashboard;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ShuffleBoardRegister {
    private final List<ShuffleBoardEntry<?>> entries = new ArrayList<>();

    public void updateEntries() {
        entries.forEach(entry -> entry.getEntry().setValue(entry.getEntryValue().get()));
    }

    public void addEntry(String tabName, String entryName, Supplier<Object> entryValue) {
        var rawEntry = Shuffleboard.getTab(tabName).add(entryName, entryValue.get()).getEntry();
        var shuffleBoardEntry = ShuffleBoardEntry
                .builder()
                .entryName(entryName)
                .entryValue(entryValue)
                .entry(rawEntry)
                .build();
        entries.add(shuffleBoardEntry);
    }
}
