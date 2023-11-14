package net.ironpulse.armbot.looper;

import edu.wpi.first.wpilibj.Timer;
import net.ironpulse.armbot.dashboard.ShuffleBoardRegister;

import java.util.List;

public class UpdateManager {
    private final List<IUpdatable> tasks;

    private double lastLoopTime;

    private final ShuffleBoardRegister shuffleBoardRegister;

    public UpdateManager(ShuffleBoardRegister shuffleBoardRegister, IUpdatable... tasks) {
        this.shuffleBoardRegister = shuffleBoardRegister;
        this.tasks = List.of(tasks);
    }

    public void init() {
        for (var task : tasks) {
            task.init();
        }
    }

    public void update() {
        shuffleBoardRegister.updateEntries();

        for (var task : tasks) {
            var timestamp = Timer.getFPGATimestamp();
            var deltaTime = timestamp - lastLoopTime;
            task.read(timestamp, deltaTime);
            task.update(timestamp, deltaTime);
            task.write(timestamp, deltaTime);
            lastLoopTime = timestamp;
        }
    }
}
