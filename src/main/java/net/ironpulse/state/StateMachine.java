package net.ironpulse.state;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StateMachine<T extends Enum<T>> {
    private final List<StateTransition<T>> transitions = new ArrayList<>();
    private final List<Value<?>> values = new ArrayList<>();

    @Getter
    private T currentState;

    public StateTransition<T> transition() {
        StateTransition<T> transition = StateTransition.builder();
        transitions.add(transition);
        return transition;
    }

    public <E> void setValue(String name, E value) {
        values.add(Value
                .builder()
                .name(name)
                .value(value)
                .build());
    }

    public void update() {
        for (var value : values) {
            var transition = transitions
                    .stream()
                    .filter(x -> x.getCondition().getName().equals(value.getName()))
                    .findFirst();
            if (transition.isEmpty()) break;
            if (!transition
                    .get()
                    .getCondition()
                    .getPredicate()
                    .test(value.getValue())) {
                break;
            }
            currentState = transition.get().getNextState();
        }
    }
}
