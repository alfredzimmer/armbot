package net.ironpulse.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import net.ironpulse.Constants;
import net.ironpulse.drivers.gyros.IGyro;
import net.ironpulse.drivers.swerve.ISwerveModule;
import net.ironpulse.drivers.swerve.SwerveModuleFactory;
import net.ironpulse.drivers.swerve.SwerveModuleType;
import net.ironpulse.looper.IUpdatable;
import net.ironpulse.models.SwerveModuleConfiguration;
import net.ironpulse.state.Condition;
import net.ironpulse.state.StateMachine;

import java.util.List;
import java.util.function.Predicate;

public class SwerveSubsystem extends SubsystemBase implements IUpdatable {
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

    private final StateMachine<MyState> stateMachine = new StateMachine<>();

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
        stateMachine
                .transition()
                .currentState(MyState.RUNNING)
                .nextState(MyState.WALKING)
                .condition(
                        Condition
                                .<Integer>builder()
                                .name("abc")
                                .predicate(x -> x > 10)
                                .build()
                )
                .build();
    }

    enum MyState {
        RUNNING, WALKING
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
        for (ISwerveModule module : swerveModules) {
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
