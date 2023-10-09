package net.ironpulse.swerve;

import net.ironpulse.models.SwerveModuleConfiguration;

public class SwerveModuleFactory {
    public static ISwerveModule createSwerveModule(SwerveModuleType type, SwerveModuleConfiguration config) {
        switch (type) {
            case SJTUMK5I:
                return new SJTUMK5iModule(config);
            default:
                return null;
        }
    }
}
