package net.ironpulse.subsystems;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import net.ironpulse.Constants;
import net.ironpulse.drivers.gyros.IGyro;
import net.ironpulse.models.SwerveModuleConfiguration;
import net.ironpulse.swerve.ISwerveModule;
import net.ironpulse.swerve.SwerveModuleFactory;
import net.ironpulse.swerve.SwerveModuleType;

import java.util.ArrayList;

public class SwerveSubsystem extends SubsystemBase {
    private final ArrayList<ISwerveModule> swerveModules = new ArrayList<>();

    private final IGyro gyro;

    private final SwerveDriveOdometry swerveDriveOdometry;

    public SwerveSubsystem(IGyro gyro) {
        this.gyro = gyro;
        swerveModules.add(
                SwerveModuleFactory.createSwerveModule(
                        SwerveModuleType.SJTUMK5I,
                        SwerveModuleConfiguration.builder()
                                .angleMotorChannel(0)
                                .driveMotorChannel(1)
                                .build()
                )
        );
        swerveModules.add(
                SwerveModuleFactory.createSwerveModule(
                        SwerveModuleType.SJTUMK5I,
                        SwerveModuleConfiguration.builder()
                                .angleMotorChannel(0)
                                .driveMotorChannel(1)
                                .build()
                )
        );
        swerveDriveOdometry = new SwerveDriveOdometry(
                new SwerveDriveKinematics(),
                gyro.getRoll(),
                new SwerveModulePosition[] {
                        swerveModules.get(0).getPosition(),
                        swerveModules.get(1).getPosition(),
                }
        );
    }

    public void drive(double xSpeed, double ySpeed, double rotation, boolean fieldRelative) {
        var swerveModuleStates = Constants.SwerveConstants.SWERVE_DRIVE_KINEMATICS.toSwerveModuleStates(
                fieldRelative ?
                        ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotation, gyro.getRoll()) :
                        new ChassisSpeeds(xSpeed, ySpeed, rotation)
        );
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, 1);
        swerveModules.get(0).setDesiredState(swerveModuleStates[0]);
        swerveModules.get(1).setDesiredState(swerveModuleStates[1]);
    }

    @Override
    public void periodic() {
        swerveDriveOdometry.update(
                gyro.getRoll(),
                new SwerveModulePosition[] {
                        swerveModules.get(0).getPosition(),
                        swerveModules.get(1).getPosition(),
                }
        );
    }
}
