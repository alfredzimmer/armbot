package net.ironpulse.armbot.state;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is a State Machine framework for easier use of FSM (Finite State Machine)
 * <p>
 * Example:
 * <pre>
 *     private final StateMachine stateMachine = new StateMachine(
 *             State.START,
 *             Transition
 *                     .builder()
 *                     .currentState(State.START)
 *                     .nextState(State.GOAL1)
 *                     .action(Action.DO_GOAL1)
 *                     .build(),
 *             Transition
 *                     .builder()
 *                     .currentState(State.GOAL1)
 *                     .nextState(State.GOAL2)
 *                     .action(Action.DO_GOAL2)
 *                     .build()
 *     );
 * </pre>
 * <pre>
 *     private enum State {
 *         START, GOAL1, GOAL2
 *     }
 * </pre>
 * <pre>
 *     private enum Action {
 *         DO_GOAL1, DO_GOAL2
 *     }
 * </pre>
 * <pre>
 *     stateMachine.transfer(Action.DO_GOAL1);
 * </pre>
 */
public class StateMachine {
    private final List<Transition> transitions;

    @Getter
    private Enum<?> currentState;

    public StateMachine(Enum<?> initialState, net.ironpulse.armbot.state.Transition... transitions) {
        currentState = initialState;
        this.transitions = List.of(transitions);
    }

    /**
     * Do an action to trigger a specific transition
     * @param action The action that is going to be done
     */
    public void transfer(Enum<?> action) {
        var transition = transitions
                .stream()
                .filter(saTransition ->
                        saTransition.getAction().equals(action)
                                && saTransition.getCurrentState().equals(currentState))
                .findFirst();
        if (transition.isEmpty()) return;
        currentState = transition.get().getNextState();
        Optional.ofNullable(transition.get().getCommand())
                .ifPresent(command ->
                        CommandScheduler
                                .getInstance()
                                .schedule(command)
                );
    }
}
