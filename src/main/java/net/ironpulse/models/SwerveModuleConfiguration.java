package net.ironpulse.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SwerveModuleConfiguration {
    private int angleMotorChannel;
    private int driveMotorChannel;

    private double kP;
    private double kI;
    private double kD;
}
