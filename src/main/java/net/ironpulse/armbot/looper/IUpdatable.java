package net.ironpulse.armbot.looper;

public interface IUpdatable {
    default void init() {

    }

    /**
     * Read data from hardware (e.g. Falcon's position)
     * @param time Current timestamp
     * @param deltaTime The delta time after last update
     */
    default void read(double time, double deltaTime) {

    }

    /**
     * Apply the data read from read() to some temporary field
     * @param time Current timestamp
     * @param deltaTime The delta time after last update
     */
    default void update(double time, double deltaTime) {

    }

    /**
     * Apply the read data to hardware
     * @param time Current timestamp
     * @param deltaTime The delta time after last update
     */
    default void write(double time, double deltaTime) {

    }

    /**
     * Update data to ShuffleBoard & Logger
     */
    default void telemetry() {

    }
}
