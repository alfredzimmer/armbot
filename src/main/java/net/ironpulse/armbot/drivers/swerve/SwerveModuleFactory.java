package net.ironpulse.armbot.drivers.swerve;

import net.ironpulse.armbot.models.SwerveModuleConfiguration;

public class SwerveModuleFactory {
    public static ISwerveModule createSwerveModule(SwerveModuleType type, SwerveModuleConfiguration config) {
        return type.getConstructor().apply(config);
    }
}
