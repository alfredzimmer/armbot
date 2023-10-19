package net.ironpulse.armbot.state;

import edu.wpi.first.wpilibj2.command.CommandBase;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transition {
    private Enum<?> currentState;

    private Enum<?> nextState;

    /**
     * The action required to do this transition
     */
    private Enum<?> action;

    /**
     * The command that will be executed after this transition
     */
    private CommandBase command;
}
