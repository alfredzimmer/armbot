package net.ironpulse.armbot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import net.ironpulse.armbot.Constants;
import net.ironpulse.armbot.dashboard.ShuffleBoardRegister;
import net.ironpulse.armbot.drivers.gyros.IGyro;
import net.ironpulse.armbot.drivers.swerve.ISwerveModule;
import net.ironpulse.armbot.drivers.swerve.SwerveModuleFactory;
import net.ironpulse.armbot.drivers.swerve.SwerveModuleType;
import net.ironpulse.armbot.looper.IUpdatable;
import net.ironpulse.armbot.models.SwerveModuleConfiguration;

import java.util.List;

import static net.ironpulse.armbot.Constants.SwerveConstants;

public class SwerveSubsystem extends SubsystemBase implements IUpdatable {
    private final List<ISwerveModule> swerveModules = List.of(
            SwerveModuleFactory.createSwerveModule(
                    SwerveModuleType.SJTUMK5,
                    SwerveModuleConfiguration
                            .builder()
                            .moduleNumber(0)
                            .angleMotorChannel(SwerveConstants.FRONT_LEFT_ANGLE_MOTOR_PORT)
                            .driveMotorChannel(SwerveConstants.FRONT_LEFT_DRIVE_MOTOR_PORT)
                            .kP(0.5)
                            .kI(0)
                            .kD(0)
                            .build()
            ),
            SwerveModuleFactory.createSwerveModule(
                    SwerveModuleType.SJTUMK5,
                    SwerveModuleConfiguration
                            .builder()
                            .moduleNumber(1)
                            .angleMotorChannel(SwerveConstants.REAR_RIGHT_ANGLE_MOTOR_PORT)
                            .driveMotorChannel(SwerveConstants.REAR_RIGHT_DRIVE_MOTOR_PORT)
                            .kP(0.5)
                            .kI(0)
                            .kD(0)
                            .build()
            )
    );

    private final IGyro gyro;

    private final SwerveDriveOdometry swerveDriveOdometry;

    public SwerveSubsystem(IGyro gyro) {
        this.gyro = gyro;
        swerveDriveOdometry = new SwerveDriveOdometry(
                SwerveConstants.SWERVE_DRIVE_KINEMATICS,
                gyro.getYaw(),
                new SwerveModulePosition[]{
                        swerveModules.get(0).getPosition(),
                        swerveModules.get(1).getPosition(),
                }
        );
        for (var swerveModule : swerveModules) {
            ShuffleBoardRegister.getInstance().addEntry(
                    "Swerve",
                    String.format("Swerve Module #%d Speed", swerveModule.getModuleNumber()),
                    () -> swerveModule.getState().speedMetersPerSecond
            );
            ShuffleBoardRegister.getInstance().addEntry(
                    "Swerve",
                    String.format("Swerve Module #%d Angle", swerveModule.getModuleNumber()),
                    () -> swerveModule.getState().angle
            );
        }
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative) {
        var swerveModuleStates = Constants.SwerveConstants.SWERVE_DRIVE_KINEMATICS.toSwerveModuleStates(
                fieldRelative ?
                        ChassisSpeeds
                                .fromFieldRelativeSpeeds(
                                        translation.getX(),
                                        translation.getY(),
                                        rotation,
                                        gyro.getYaw()
                                )
                        :
                        new ChassisSpeeds(translation.getX(), translation.getY(), rotation)
        );
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.SwerveConstants.MAX_SPEED);
        for (var module : swerveModules) {
            module.setDesiredState(swerveModuleStates[module.getModuleNumber()]);
        }
    }

    public void zeroGyro() {
        gyro.setYaw(0);
    }

    public SwerveModuleState[] getStates() {
        var states = new SwerveModuleState[swerveModules.size()];
        for (var module : swerveModules) {
            states[module.getModuleNumber()] = module.getState();
        }
        return states;
    }

    @Override
    public void update(double time, double deltaTime) {
        swerveDriveOdometry.update(
                gyro.getYaw(),
                new SwerveModulePosition[] {
                        swerveModules.get(0).getPosition(),
                        swerveModules.get(1).getPosition(),
                }
        );
    }
}
