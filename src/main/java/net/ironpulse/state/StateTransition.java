package net.ironpulse.state;

import lombok.Getter;

@Getter
public class StateTransition<T extends Enum<T>> {
    private T currentState;
    private T nextState;

    private Condition<?> condition;

    public static <T extends Enum<T>> StateTransition<T> builder() {
        return new StateTransition<>();
    }

    public StateTransition<T> currentState(T currentState) {
        this.currentState = currentState;
        return this;
    }

    public StateTransition<T> nextState(T nextState) {
        this.nextState = nextState;
        return this;
    }

    public StateTransition<T> condition(Condition<?> condition) {
        this.condition = condition;
        return this;
    }

    public StateTransition<T> build() {
        return this;
    }
}
