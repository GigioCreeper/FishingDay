package it.unicam.cs.mpgc.rpg129382.model.geometry;

import java.util.Random;

/**
 * The horizontal direction something faces or moves to.
 */
public enum Heading {
    /**
     * Towards the left side of the screen.
     */
    LEFT(-1),
    /**
     * Towards the right side of the screen.
     */
    RIGHT(1);

    private final int sign;

    Heading(int sign) {
        this.sign = sign;
    }

    /**
     * {@return -1 for {@link #LEFT} and 1 for {@link #RIGHT}, to turn a speed into a horizontal offset}
     */
    public int sign() {
        return sign;
    }

    /**
     * {@return one of the two headings, with the same probability}
     *
     * @param random the source of randomness.
     */
    public static Heading random(Random random) {
        return random.nextBoolean() ? RIGHT : LEFT;
    }
}