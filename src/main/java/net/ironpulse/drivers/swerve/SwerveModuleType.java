package net.ironpulse.drivers.swerve;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.ironpulse.models.SwerveModuleConfiguration;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum SwerveModuleType {
    SJTUMK5I(SJTUMK5iModule::new);

    private final Function<SwerveModuleConfiguration, ISwerveModule> constructor;
}
