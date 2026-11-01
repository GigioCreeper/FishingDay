package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Objects;

/**
 * A bottle with a message, drifting along the surface of the lake.
 */
public class MessageBottle implements Positioned {
    private final Heading heading;
    private final double speed;
    private final double y;
    private double x;

    public MessageBottle(Heading heading, double speed, double x, double y) {
        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be positive");
        }
        this.heading = Objects.requireNonNull(heading);
        this.speed = speed;
        this.x = x;
        this.y = y;
    }

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