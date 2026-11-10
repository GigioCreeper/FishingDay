package it.unicam.cs.mpgc.rpg129382.logic.fishing.fight;

import it.unicam.cs.mpgc.rpg129382.model.Displacement;
import it.unicam.cs.mpgc.rpg129382.model.DurationRange;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Heading;

import java.util.Objects;
import java.util.Random;

/**
 * The phase in which the fish pulls the hook away, diagonally downwards, ignoring the player.
 * The pull grows with the fish strength and shrinks with the rod power; the hook may leave
 * the water, and then the line breaks.
 */
public final class PullPhase implements FightPhase {
    /** The share of the pull that goes sideways: with {@link #PULL_Y} it forms a direction of length 1. */
    private static final double PULL_X = 0.8;
    /** The share of the pull that goes downwards. */
    private static final double PULL_Y = 0.6;

    private final Random random;
    private final DurationRange durations;
    private final double rodPower;

    /**
     * Creates a pull phase for the given rod.
     *
     * @param random    the source of randomness, also used to choose where the fish pulls.
     * @param durations how long the phase can last.
     * @param rodPower  the power of the rod the player is using.
     * @throws IllegalArgumentException if the rod power is not positive.
     */
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

    /**
     * Turns the fish to a random side: it pulls that way until the phase ends.
     *
     * @param fish the hooked fish.
     */
    @Override
    public void enter(Fish fish) {
        fish.face(Heading.random(random));
    }

    @Override
    public Displacement step(Fish fish, double directionX, double directionY, double delta) {
        double distance = pullSpeed(fish) * delta;
        return new Displacement(fish.getHeading().sign() * PULL_X * distance, PULL_Y * distance);
    }

    /**
     * The pull grows with the square of the strength, so strong fish are much harder than weak ones,
     * and the rod power divides it.
     */
    private double pullSpeed(Fish fish) {
        double strength = fish.getType().strength();
        return strength * (strength / rodPower);
    }
}