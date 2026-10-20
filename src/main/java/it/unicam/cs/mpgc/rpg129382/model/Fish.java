package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Objects;

public class Fish implements Controllable {
    private final FishType type;
    private final Heading heading;
    private double x;
    private double y;

    public Fish(FishType type, Heading heading, double x, double y) {
        this.type = Objects.requireNonNull(type);
        this.heading = Objects.requireNonNull(heading);
        this.x = x;
        this.y = y;
    }

    public FishType getType() {
        return type;
    }

    public Heading getHeading() {
        return heading;
    }

    public void swim(double deltaSeconds) {
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