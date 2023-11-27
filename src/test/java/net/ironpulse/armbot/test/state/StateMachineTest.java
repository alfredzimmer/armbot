package net.ironpulse.armbot.test.state;


import net.ironpulse.armbot.state.StateMachine;
import net.ironpulse.armbot.state.Transition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class StateMachineTest {
    private enum State {
        START, GOAL1, GOAL2
    }

    private enum Action {
        DO_GOAL1, DO_GOAL2, BACK_TO_START
    }

    private static StateMachine stateMachine;

    @BeforeAll
    static void init() {
        stateMachine = new StateMachine(
                State.START,
                Transition
                        .builder()
                        .currentState(State.START)
                        .nextState(State.GOAL1)
                        .action(Action.DO_GOAL1)
                        .build(),
                Transition
                        .builder()
                        .currentState(State.GOAL1)
                        .nextState(State.GOAL2)
                        .action(Action.DO_GOAL2)
                        .build(),
                Transition.builder()
                        .currentState(State.GOAL2)
                        .nextState(State.START)
                        .action(Action.BACK_TO_START)
                        .build()
        );
    }

    @Test
    void testTransfer1() {
        stateMachine.transfer(Action.DO_GOAL1);
        assertEquals(State.GOAL1, stateMachine.getCurrentState());
    }

    @Test
    void testTransfer2() {
        stateMachine.transfer(Action.BACK_TO_START);
        assertEquals(State.GOAL1, stateMachine.getCurrentState());
    }

    @Test
    void testTransfer3() {
        stateMachine.transfer(Action.DO_GOAL2);
        assertEquals(State.GOAL2, stateMachine.getCurrentState());
    }

    @Test
    void testTransfer4() {
        stateMachine.transfer(Action.BACK_TO_START);
        assertEquals(State.START, stateMachine.getCurrentState());
    }
}
