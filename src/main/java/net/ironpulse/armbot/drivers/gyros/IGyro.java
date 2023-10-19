package net.ironpulse.armbot.drivers.gyros;

import edu.wpi.first.math.geometry.Rotation2d;

public interface IGyro {
    Rotation2d getYaw();
    Rotation2d getPitch();
    Rotation2d getRoll();
    double[] getRaw();

    void setYaw(double angle);
    void setPitch(double angle);
    void setRoll(double angle);
}
