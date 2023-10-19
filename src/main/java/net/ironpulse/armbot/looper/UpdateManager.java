package net.ironpulse.armbot.looper;

import edu.wpi.first.wpilibj.Timer;

import java.util.List;

public class UpdateManager {
    private final List<IUpdatable> tasks;

    private double lastLoopTime;

    public UpdateManager(IUpdatable... tasks) {
        this.tasks = List.of(tasks);
    }

    public void update() {
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
