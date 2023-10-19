package net.ironpulse.state;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.util.List;

public class StateMachine {
    private final List<Transition> transitions;

    private Enum<?> currentState;

    public StateMachine(Enum<?> initialState, Transition... transitions) {
        currentState = initialState;
        this.transitions = List.of(transitions);
    }

    public void transfer(Enum<?> action) {
        var transition = transitions
                .stream()
                .filter(saTransition ->
                        saTransition.getAction().equals(action)
                                && saTransition.getCurrentState().equals(currentState))
                .findFirst();
        if (transition.isEmpty()) return;

        currentState = transition.get().getNextState();
        CommandScheduler
                .getInstance()
                .schedule(transition.get().getCommand());
    }
}
