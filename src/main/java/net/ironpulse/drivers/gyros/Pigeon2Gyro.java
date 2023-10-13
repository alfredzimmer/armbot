package net.ironpulse.drivers.gyros;

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.math.geometry.Rotation2d;
import lombok.Setter;

public class Pigeon2Gyro implements IGyro {
    private final Pigeon2 gyro;

    @Setter
    private boolean inverted = false;
    private Rotation2d yawAdjustmentAngle = new Rotation2d();
    private Rotation2d rollAdjustmentAngle = new Rotation2d();
    private Rotation2d pitchAdjustmentAngle = new Rotation2d();

    public Pigeon2Gyro(int port) {
        gyro = new Pigeon2(port);
        gyro.configFactoryDefault();
    }

    @Override
    public Rotation2d getYaw() {
        var angle = Rotation2d.fromDegrees(gyro.getYaw()).minus(yawAdjustmentAngle);
        return inverted ? angle.unaryMinus() : angle;
    }

    @Override
    public Rotation2d getPitch() {
        return Rotation2d.fromDegrees(gyro.getPitch()).minus(pitchAdjustmentAngle);
    }

    @Override
    public Rotation2d getRoll() {
        return Rotation2d.fromDegrees(gyro.getRoll()).minus(rollAdjustmentAngle);
    }

    @Override
    public double[] getRaw() {
        var raw = new double[] {0, 0, 0};
        gyro.getRawGyro(raw);
        return raw;
    }

    @Override
    public void setYaw(double angle) {
        yawAdjustmentAngle = Rotation2d
                .fromDegrees(gyro.getYaw())
                .rotateBy(
                        Rotation2d
                                .fromDegrees(angle)
                                .unaryMinus()
                );
    }

    @Override
    public void setPitch(double angle) {
        pitchAdjustmentAngle = Rotation2d
                .fromDegrees(gyro.getPitch())
                .rotateBy(
                        Rotation2d
                                .fromDegrees(angle)
                                .unaryMinus()
                );
    }

    @Override
    public void setRoll(double angle) {
        rollAdjustmentAngle = Rotation2d
                .fromDegrees(gyro.getRoll())
                .rotateBy(
                        Rotation2d
                                .fromDegrees(angle)
                                .unaryMinus()
                );
    }
}
