package it.unicam.cs.mpgc.rpg129382.logic.fishing.fight;

import it.unicam.cs.mpgc.rpg129382.model.Displacement;
import it.unicam.cs.mpgc.rpg129382.model.DurationRange;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Heading;

import java.util.Objects;
import java.util.Random;

/**
 * The phase in which the fish pulls the hook away.
 * The pull grows with the fish strength and shrinks with the rod power.
 */
public final class PullPhase implements FightPhase {
    private static final double PULL_X = 0.8;
    private static final double PULL_Y = 0.6;

    private final Random random;
    private final DurationRange durations;
    private final double rodPower;

    public PullPhase(Random random, DurationRange durations, double rodPower) {
        if (rodPower <= 0) {
            throw new IllegalArgumentException("rodPower must be positive");
        }
        this.random = Objects.requireNonNull(random);
        this.durations = Objects.requireNonNull(durations);
        this.rodPower = rodPower;
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
        fish.face(Heading.random(random));
    }

    @Override
    public Displacement step(Fish fish, double directionX, double directionY, double delta) {
        double distance = pullSpeed(fish) * delta;
        return new Displacement(fish.getHeading().sign() * PULL_X * distance, PULL_Y * distance);
    }

    private double pullSpeed(Fish fish) {
        double strength = fish.getType().strength();
        return strength * (strength / rodPower);
    }
}