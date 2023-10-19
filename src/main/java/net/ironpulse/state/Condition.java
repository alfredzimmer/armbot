package net.ironpulse.state;

import lombok.Data;

import java.util.function.Predicate;

@Data
public class Condition<T> {
    private String name;

    private Predicate<T> predicate;

    public static <T> Condition<T> builder() {
        return new Condition<>();
    }

    public Condition<T> name(String name) {
        this.name = name;
        return this;
    }

    public Condition<T> predicate(Predicate<T> predicate) {
        this.predicate = predicate;
        return this;
    }

    public Condition<T> build() {
        return this;
    }
}
