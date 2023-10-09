package net.ironpulse.drivers.swerve;

import net.ironpulse.models.SwerveModuleConfiguration;

public class SwerveModuleFactory {
    public static ISwerveModule createSwerveModule(SwerveModuleType type, SwerveModuleConfiguration config) {
        switch (type) {
            case SJTUMK5I:
            default:
                return new SJTUMK5iModule(config);
        }
    }
}