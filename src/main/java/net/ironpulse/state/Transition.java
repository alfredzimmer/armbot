package net.ironpulse.state;

import edu.wpi.first.wpilibj2.command.CommandBase;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transition {
    private Enum<?> currentState;

    private Enum<?> nextState;

    private Enum<?> action;

    private CommandBase command;
}
