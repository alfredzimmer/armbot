package net.ironpulse.swerve;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public interface ISwerveModule {

    /**
     * Apply desired state to the swerve module.
     * @param desiredState The desired state
     */
    void setDesiredState(SwerveModuleState desiredState);

    /**
     * Get the position (include angle and drive motor's distance) of the swerve module.
     * @return The position of the swerve module
     */
    SwerveModulePosition getPosition();

    SwerveModuleState getState();

    int getModuleNumber();

    /**
     * Reset the swerve module
     */
    void reset();
}