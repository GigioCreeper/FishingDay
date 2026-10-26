package it.unicam.cs.mpgc.rpg129382.logic.fight;

import it.unicam.cs.mpgc.rpg129382.model.Displacement;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Heading;

import java.util.Objects;
import java.util.Random;

public final class PullPhase implements FightPhase {
    private static final double PULL_X = 0.8;
    private static final double PULL_Y = 0.6;

    private final Random random;
    private final DurationRange durations;

    public PullPhase(Random random, DurationRange durations) {
        this.random = Objects.requireNonNull(random);
        this.durations = Objects.requireNonNull(durations);
    }

    @Override
    public boolean isFishPulling() {
        return true;
    }

    @Override
    public boolean keepsHookInWater() {
        return false;
    }

    @Override
    public double nextDuration() {
        return durations.next(random);
    }

    @Override
    public void enter(Fish fish) {
        fish.face(random.nextBoolean() ? Heading.RIGHT : Heading.LEFT);
    }

    @Override
    public Displacement step(Fish fish, double directionX, double directionY, double delta) {
        double distance = fish.getType().strength() * delta;
        return new Displacement(fish.getHeading().sign() * PULL_X * distance, PULL_Y * distance);
    }
}
