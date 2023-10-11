package net.ironpulse.drivers.swerve;

import net.ironpulse.models.SwerveModuleConfiguration;

public class SwerveModuleFactory {
    public static ISwerveModule createSwerveModule(SwerveModuleType type, SwerveModuleConfiguration config) {
        return type.getConstructor().apply(config);
    }
}
