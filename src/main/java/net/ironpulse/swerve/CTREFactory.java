package net.ironpulse.swerve;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import net.ironpulse.drivers.LazyTalonSRX;

public class CTREFactory {

    public static TalonFX createDefaultTalonFX(int id, boolean isOnCanivore) {
        var talon = new TalonFX(id, isOnCanivore ? "canivore" : "");
        talon.configFactoryDefault();

        return talon;
    }

    public static TalonSRX createDefaultTalonSRX(int id) {
        TalonSRX talon = new LazyTalonSRX(id);
        talon.configFactoryDefault();

        return talon;
    }
}
