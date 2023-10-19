package net.ironpulse.armbot.maths;

public class Conversions {
    /**
     * @param counts Falcon Counts
     * @param gearRatio Gear Ratio between Falcon and Mechanism
     * @return Degrees of Rotation of Mechanism
     */
    public static double falconToDegrees(double counts, double gearRatio) {
        return falconToDegrees(counts, gearRatio, 2048);
    }

    /**
     * @param counts Falcon Counts
     * @param gearRatio Gear Ratio between Falcon and Mechanism
     * @return Degrees of Rotation of Mechanism
     */
    public static double falconToDegrees(double counts, double gearRatio, double rotation) {
        return counts * (360.0 / (gearRatio * rotation));
    }

    /**
     * @param degrees Degrees of rotation of Mechanism
     * @param gearRatio Gear Ratio between Falcon and Mechanism
     * @return Falcon Counts
     */
    public static double degreesToFalcon(double degrees, double gearRatio) {
        return degreesToFalcon(degrees, gearRatio, 2048);
    }

    /**
     * @param degrees Degrees of rotation of Mechanism
     * @param gearRatio Gear Ratio between Falcon and Mechanism
     * @return Falcon Counts
     */
    public static double degreesToFalcon(double degrees, double gearRatio, double rotation) {
        return degrees / (360.0 / (gearRatio * rotation));
    }

    /**
     * @param velocityCounts Falcon Velocity Counts
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return RPM of Mechanism
     */
    public static double falconToRPM(double velocityCounts, double gearRatio) {
        return falconToRPM(velocityCounts, gearRatio, 2048);
    }

    /**
     * @param rpm RPM of mechanism
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return RPM of Mechanism
     */
    public static double rpmToFalcon(double rpm, double gearRatio) {
        return rpmToFalcon(rpm, gearRatio, 2048);
    }

    /**
     * @param velocityCounts Falcon Velocity Counts
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return Falcon Velocity Counts
     */
    public static double falconToMPS(double velocityCounts, double circumference, double gearRatio) {
        return falconToMPS(velocityCounts, circumference, gearRatio, 2048);
    }

    /**
     * @param velocity Velocity MPS
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return Falcon Velocity Counts
     */
    public static double mpsToFalcon(double velocity, double circumference, double gearRatio) {
        return mpsToFalcon(velocity, circumference, gearRatio, 2048);
    }

    /**
     * @param velocityCounts Falcon Velocity Counts
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return RPM of Mechanism
     */
    public static double falconToRPM(double velocityCounts, double gearRatio, double rotation) {
        double motorRPM = velocityCounts * (600.0 / rotation);
        return motorRPM / gearRatio;
    }

    /**
     * @param RPM RPM of mechanism
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return RPM of Mechanism
     */
    public static double rpmToFalcon(double RPM, double gearRatio, double rotation) {
        double motorRPM = RPM * gearRatio;
        return motorRPM * (rotation / 600);
    }

    /**
     * @param velocityCounts Falcon Velocity Counts
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return Falcon Velocity Counts
     */
    public static double falconToMPS(double velocityCounts, double circumference, double gearRatio, double rotation) {
        double wheelRPM = falconToRPM(velocityCounts, gearRatio, rotation);
        return (wheelRPM * circumference) / 60;
    }

    /**
     * @param velocity Velocity MPS
     * @param circumference Circumference of Wheel
     * @param gearRatio Gear Ratio between Falcon and Mechanism (set to 1 for Falcon RPM)
     * @return Falcon Velocity Counts
     */
    public static double mpsToFalcon(double velocity, double circumference, double gearRatio, double rotation) {
        double wheelRPM = ((velocity * 60) / circumference);
        return rpmToFalcon(wheelRPM, gearRatio, rotation);
    }
}
