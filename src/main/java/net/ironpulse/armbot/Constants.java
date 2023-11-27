// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package net.ironpulse.armbot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static class OperatorConstants {
        public static final int DRIVER_CONTROLLER_PORT = 0;
    }

    public static class SwerveConstants {
        public static final int FRONT_LEFT_DRIVE_MOTOR_PORT = 1;
        public static final int FRONT_LEFT_ANGLE_MOTOR_PORT = 0;

        public static final int REAR_RIGHT_DRIVE_MOTOR_PORT = 2;
        public static final int REAR_RIGHT_ANGLE_MOTOR_PORT = 3;

        public static final double TRACK_WIDTH = 0.5;

        // Distance between centers of right and left wheels on robot
        public static final double WHEEL_BASE = 0.5;

        public static final SwerveDriveKinematics SWERVE_DRIVE_KINEMATICS =
            new SwerveDriveKinematics(
                new Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2),
                new Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2)
            );

        public static final double WHEEL_CIRCUMFERENCE_METERS = 0.1;
        public static final double DRIVE_GEAR_RATIO = 6.75;
        public static final double ANGLE_GEAR_RATIO = 21.42;

        public static final double MAX_SPEED = 0.5;
    }

    public static class SensorConstants {
        public static final int GYRO_PORT = 1;
    }
}
