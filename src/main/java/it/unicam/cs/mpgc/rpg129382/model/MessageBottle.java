package it.unicam.cs.mpgc.rpg129382.model;

import it.unicam.cs.mpgc.rpg129382.model.geometry.Heading;
import it.unicam.cs.mpgc.rpg129382.model.geometry.Positioned;

import java.util.Objects;

/**
 * A bottle with a message, drifting along the surface of the lake.
 */
public class MessageBottle implements Positioned {
    private final Heading heading;
    private final double speed;
    private final double y;
    private double x;

    /**
     * Creates a bottle drifting along the surface.
     *
     * @param heading the direction the bottle drifts to.
     * @param speed   how fast the bottle drifts, in pixels per second.
     * @param x       the starting horizontal position.
     * @param y       the height at which the bottle floats.
     * @throws IllegalArgumentException if the speed is not positive.
     */
    public MessageBottle(Heading heading, double speed, double x, double y) {
        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be positive");
        }
        this.heading = Objects.requireNonNull(heading);
        this.speed = speed;
        this.x = x;
        this.y = y;
    }

    /**
     * Drifts horizontally along the surface.
     *
     * @param deltaSeconds the seconds elapsed since the last tick.
     */
    public void drift(double deltaSeconds) {
        x += heading.sign() * speed * deltaSeconds;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}