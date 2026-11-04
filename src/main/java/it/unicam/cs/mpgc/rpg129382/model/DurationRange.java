package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Random;

/**
 * A range of durations, in seconds, from which a random one can be drawn.
 *
 * @param min the shortest duration.
 * @param max the longest duration.
 */
public record DurationRange(double min, double max) {

    /**
     * @throws IllegalArgumentException if min is not positive or max is less than min.
     */
    public DurationRange {
        if (min <= 0 || max < min) {
            throw new IllegalArgumentException("invalid duration range");
        }
    }

    /**
     * @param random the source of randomness.
     * @return a random duration between min and max, or exactly min if they are equal.
     */
    public double next(Random random) {
        return min == max ? min : random.nextDouble(min, max);
    }
}
