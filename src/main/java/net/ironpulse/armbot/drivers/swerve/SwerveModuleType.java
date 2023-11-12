package net.ironpulse.armbot.drivers.swerve;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.ironpulse.armbot.models.SwerveModuleConfiguration;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum SwerveModuleType {
    SJTUMK5(SJTUMK5Module::new);

    private final Function<SwerveModuleConfiguration, ISwerveModule> constructor;
}
