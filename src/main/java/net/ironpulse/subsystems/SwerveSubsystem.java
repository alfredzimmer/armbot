package net.ironpulse.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import net.ironpulse.Constants;
import net.ironpulse.drivers.gyros.IGyro;
import net.ironpulse.models.SwerveModuleConfiguration;
import net.ironpulse.swerve.ISwerveModule;
import net.ironpulse.swerve.SwerveModuleFactory;
import net.ironpulse.swerve.SwerveModuleType;

import java.util.List;

public class SwerveSubsystem extends SubsystemBase {
    private final List<ISwerveModule> swerveModules = List.of(
            SwerveModuleFactory.createSwerveModule(
                    SwerveModuleType.SJTUMK5I,
                    SwerveModuleConfiguration.builder()
                            .angleMotorChannel(0)
                            .driveMotorChannel(1)
                            .build()
            ),
            SwerveModuleFactory.createSwerveModule(
                    SwerveModuleType.SJTUMK5I,
                    SwerveModuleConfiguration.builder()
                            .angleMotorChannel(0)
                            .driveMotorChannel(1)
                            .build()
            )
    );

    private final IGyro gyro;

    private final SwerveDriveOdometry swerveDriveOdometry;

    public SwerveSubsystem(IGyro gyro) {
        this.gyro = gyro;
        swerveDriveOdometry = new SwerveDriveOdometry(
                Constants.SwerveConstants.SWERVE_DRIVE_KINEMATICS,
                gyro.getYaw(),
                new SwerveModulePosition[] {
                        swerveModules.get(0).getPosition(),
                        swerveModules.get(1).getPosition(),
                }
        );
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
        for (ISwerveModule module : swerveModules) {
            module.setDesiredState(swerveModuleStates[module.getModuleNumber()]);
        }
    }

    public void zeroGyro() {
        gyro.setYaw(0);
    }

    public SwerveModuleState[] getStates() {
        SwerveModuleState[] states = new SwerveModuleState[4];
        for (ISwerveModule module : swerveModules) {
            states[module.getModuleNumber()] = module.getState();
        }
        return states;
  }

    @Override
    public void periodic() {
        swerveDriveOdometry.update(
                gyro.getYaw(),
                new SwerveModulePosition[] {
                        swerveModules.get(0).getPosition(),
                        swerveModules.get(1).getPosition(),
                }
        );
    }
}
