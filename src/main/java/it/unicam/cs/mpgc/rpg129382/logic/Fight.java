package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.model.Fish;

import java.util.Objects;
import java.util.Random;

public class Fight {
    private static final double PULL_X = 0.8;
    private static final double PULL_Y = 0.6;

    private final Fish fish;
    private final Random random;
    private boolean fishPulling = true;
    private double timeLeft;

    public Fight(Fish fish, Random random) {
        this.fish = Objects.requireNonNull(fish);
        this.random = Objects.requireNonNull(random);
        this.timeLeft = pullDuration();
    }

    public Fish getFish() {
        return fish;
    }

    public boolean isFishPulling() {
        return fishPulling;
    }

    public double pullDirectionX() {
        return fish.getHeading().sign() * PULL_X;
    }

    public double pullDirectionY() {
        return PULL_Y;
    }

    public double pullSpeed() {
        return fish.getType().strength();
    }

    public void update(double delta) {
        timeLeft -= delta;
        if (timeLeft <= 0) {
            fishPulling = !fishPulling;
            timeLeft = fishPulling ? pullDuration() : controlDuration();
        }
    }

    private double pullDuration() {
        return random.nextDouble(GameConfig.PULL_MIN_SECONDS, GameConfig.PULL_MAX_SECONDS);
    }

    private double controlDuration() {
        return random.nextDouble(GameConfig.CONTROL_MIN_SECONDS, GameConfig.CONTROL_MAX_SECONDS);
    }
}