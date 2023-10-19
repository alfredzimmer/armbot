package net.ironpulse.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lombok.Getter;
import net.ironpulse.Constants;
import net.ironpulse.drivers.gyros.IGyro;
import net.ironpulse.drivers.swerve.ISwerveModule;
import net.ironpulse.drivers.swerve.SwerveModuleFactory;
import net.ironpulse.drivers.swerve.SwerveModuleType;
import net.ironpulse.looper.IUpdatable;
import net.ironpulse.models.SwerveModuleConfiguration;
import net.ironpulse.state.StateMachine;
import net.ironpulse.state.Transition;

import java.util.List;

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

    @Getter
    private final StateMachine stateMachine = new StateMachine(
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
                    .build()
    );

    private enum State {
        START, GOAL1, GOAL2
    }

    private enum Action {
        DO_GOAL1, DO_GOAL2
    }

    public SwerveSubsystem(IGyro gyro) {
        this.gyro = gyro;
        swerveDriveOdometry = new SwerveDriveOdometry(
                Constants.SwerveConstants.SWERVE_DRIVE_KINEMATICS,
                gyro.getYaw(),
                new SwerveModulePosition[]{
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
        for (var module : swerveModules) {
            module.setDesiredState(swerveModuleStates[module.getModuleNumber()]);
        }
        stateMachine.transfer(Action.DO_GOAL1);
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
