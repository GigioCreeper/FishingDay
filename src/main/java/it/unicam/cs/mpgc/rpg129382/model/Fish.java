package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Objects;

public class Fish implements ReadOnlyFish, Movable {
    private final FishType type;
    private Heading heading;
    private double x;
    private double y;
    private boolean hooked;

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

    public void hook() {
        hooked = true;
    }

    public void face(Heading heading) {
        this.heading = Objects.requireNonNull(heading);
    }

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