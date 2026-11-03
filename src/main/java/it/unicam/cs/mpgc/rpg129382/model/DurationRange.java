package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Random;

/**
 * A range of durations, in seconds, from which a random one can be drawn.
 */
public record DurationRange(double min, double max) {

    public DurationRange {
        if (min <= 0 || max < min) {
            throw new IllegalArgumentException("invalid duration range");
        }
    }

    public double next(Random random) {
        return min == max ? min : random.nextDouble(min, max);
    }
}
