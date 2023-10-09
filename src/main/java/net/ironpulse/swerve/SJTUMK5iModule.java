package net.ironpulse.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import net.ironpulse.Constants;
import net.ironpulse.maths.AngleNormalization;
import net.ironpulse.maths.Conversions;
import net.ironpulse.models.SwerveModuleConfiguration;

public class SJTUMK5iModule implements ISwerveModule {

    private final TalonSRX angleMotor;

    private final TalonFX driveMotor;

    private final int moduleNumber;

    public SJTUMK5iModule(SwerveModuleConfiguration config) {
        driveMotor = CTREFactory.createDefaultTalonFX(config.getDriveMotorChannel(), true);
        this.moduleNumber = config.getModuleNumber();
        driveMotor.config_kP(0, config.getKP());
        driveMotor.config_kI(0, config.getKI());
        driveMotor.config_kD(0, config.getKD());

        angleMotor = CTREFactory.createDefaultTalonSRX(config.getAngleMotorChannel());
        angleMotor.config_kP(0, config.getKP());
        angleMotor.config_kI(0, config.getKI());
        angleMotor.config_kD(0, config.getKD());
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
                Conversions.degreesToFalcon(state.angle.getDegrees(), 1.0, 4096));
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
                Conversions.falconToDegrees(angleMotor.getSelectedSensorPosition(), 1.0));
    }

    private Rotation2d getEncoderAngle() {
        return Rotation2d.fromDegrees(
                AngleNormalization.getAbsoluteAngleDegree(getEncoderAngleUnbound().getDegrees()));
    }
}
