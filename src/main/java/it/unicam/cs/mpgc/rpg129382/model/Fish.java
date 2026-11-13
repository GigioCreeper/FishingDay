package it.unicam.cs.mpgc.rpg129382.model;

import it.unicam.cs.mpgc.rpg129382.model.geometry.Heading;
import it.unicam.cs.mpgc.rpg129382.model.geometry.Movable;

import java.util.Objects;

/**
 * A fish swimming in the lake. It swims straight in the direction it faces until it gets hooked;
 * from then on it stays attached to the hook, and its pulls during the fight move them both.
 */
public class Fish implements ReadOnlyFish, Movable {
    private final FishType type;
    private Heading heading;
    private double x;
    private double y;
    private boolean hooked;

    /**
     * Creates a free fish, not hooked yet.
     *
     * @param type    the type of the fish.
     * @param heading the direction the fish faces and swims to.
     * @param x       the starting horizontal position.
     * @param y       the starting vertical position.
     */
    public Fish(FishType type, Heading heading, double x, double y) {
        this.type = Objects.requireNonNull(type);
        this.heading = Objects.requireNonNull(heading);
        this.x = x;
        this.y = y;
    }

    @Override
    public FishType getType() {
        return type;
    }

    @Override
    public Heading getHeading() {
        return heading;
    }

    @Override
    public boolean isHooked() {
        return hooked;
    }

    /**
     * Catches the fish on the hook: from now on {@link #swim(double)} does nothing,
     * and the fight decides how the fish moves.
     */
    public void hook() {
        hooked = true;
    }

    /**
     * Turns the fish, without moving it.
     *
     * @param heading the new direction to face.
     */
    public void face(Heading heading) {
        this.heading = Objects.requireNonNull(heading);
    }

    /**
     * Swims forward at the speed of its type. A hooked fish does not swim.
     *
     * @param deltaSeconds the seconds elapsed since the last tick.
     */
    public void swim(double deltaSeconds) {
        if (hooked) {
            return;
        }
        move(heading.sign() * type.speed() * deltaSeconds, 0);
    }

    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
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