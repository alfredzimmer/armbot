package net.ironpulse.drivers.swerve;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum TalonType {
    SRX(TalonSRX::new),
    FX(TalonFX::new);

    private final Function<Integer, BaseTalon> constructor;
}
