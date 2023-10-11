package net.ironpulse.drivers.swerve;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;

import java.util.function.Function;

public class CTREFactory {
    public static BaseTalon createDefaultTalon(int id, TalonType type) {
        return type.getConstructor().apply(id);
    }
    public static BaseTalon createTalon(int id, TalonType type, Function<BaseTalon, BaseTalon> config) {
        return config.apply(type.getConstructor().apply(id));
    }
}
