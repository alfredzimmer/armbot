package net.ironpulse.armbot.drivers.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import net.ironpulse.armbot.Constants;
import net.ironpulse.armbot.maths.AngleNormalization;
import net.ironpulse.armbot.maths.Conversions;
import net.ironpulse.armbot.models.SwerveModuleConfiguration;

public class SJTUMK5Module implements ISwerveModule {

    private final TalonFX angleMotor;

    private final TalonFX driveMotor;

    private final int moduleNumber;

    public SJTUMK5Module(SwerveModuleConfiguration config) {
        driveMotor = (TalonFX) CTREFactory.createTalon(
                config.getDriveMotorChannel(),
                TalonType.FX,
                talon -> {
                    talon.config_kP(0, config.getKP());
                    talon.config_kI(0, config.getKI());
                    talon.config_kD(0, config.getKD());
                    return talon;
                }
        );
        this.moduleNumber = config.getModuleNumber();

        angleMotor = (TalonFX) CTREFactory.createTalon(
                config.getAngleMotorChannel(),
                TalonType.FX,
                talon -> {
                    talon.configMotionAcceleration(50000);
                    talon.configMotionCruiseVelocity(30000);
                    return talon;
                }
        );
    }

    @Override
    public void setDesiredState(SwerveModuleState desiredState) {
        var state = SwerveModuleState.optimize(desiredState, getEncoderAngleUnbound());
        driveMotor.set(ControlMode.Velocity,
                Conversions.mpsToFalcon(
                        state.speedMetersPerSecond,
                        Constants.SwerveConstants.WHEEL_CIRCUMFERENCE_METERS,
                        Constants.SwerveConstants.DRIVE_GEAR_RATIO
                )
        );
        angleMotor.set(ControlMode.MotionMagic,
                Conversions.degreesToFalcon(state.angle.getDegrees(), Constants.SwerveConstants.ANGLE_GEAR_RATIO, 4096));
    }

    @Override
    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
                driveMotor.getSelectedSensorPosition(),
                getEncoderAngleUnbound()
        );
    }

    @Override
    public SwerveModuleState getState() {
        var velocity = Conversions.falconToMPS(driveMotor.getSelectedSensorVelocity(),
                Constants.SwerveConstants.WHEEL_CIRCUMFERENCE_METERS, Constants.SwerveConstants.DRIVE_GEAR_RATIO);
        var angle = getEncoderAngle();
        return new SwerveModuleState(velocity, angle);
    }

    @Override
    public int getModuleNumber() {
        return moduleNumber;
    }

    @Override
    public void reset() {
        driveMotor.set(ControlMode.Velocity, 0);
        angleMotor.setSelectedSensorPosition(0);
    }

    /**
     * Get the Encoder angle unbound (maybe greater than 360 or lower than 0) with
     * angle offset calculated.
     *
     * @return The raw angle of the encoder in degrees.
     */
    private Rotation2d getEncoderAngleUnbound() {
        return Rotation2d.fromDegrees(
                Conversions.falconToDegrees(angleMotor.getSelectedSensorPosition(), Constants.SwerveConstants.ANGLE_GEAR_RATIO));
    }

    private Rotation2d getEncoderAngle() {
        return Rotation2d.fromDegrees(
                AngleNormalization.getAbsoluteAngleDegree(getEncoderAngleUnbound().getDegrees()));
    }
}
