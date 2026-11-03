package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CatchDetector {
    private final double radius;

    public CatchDetector(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be positive");
        }
        this.radius = radius;
    }

    public Optional<Fish> findCatch(Positioned hook, List<Fish> fishes) {
        Objects.requireNonNull(hook);
        Objects.requireNonNull(fishes);
        return fishes.stream()
                .filter(fish -> touches(hook, fish))
                .findFirst();
    }

    /**
     * @param hook   the hook.
     * @param target something in the water.
     * @return true if the target is close enough to the hook to be caught.
     */
    public boolean touches(Positioned hook, Positioned target) {
        Objects.requireNonNull(hook);
        Objects.requireNonNull(target);
        double distance = Math.hypot(target.getX() - hook.getX(), target.getY() - hook.getY());
        return distance <= radius;
    }
}