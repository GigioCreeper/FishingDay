package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.geometry.Positioned;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Decides when the hook is close enough to something to catch it.
 */
public class CatchDetector {
    private final double radius;

    /**
     * Creates a detector with the given catch radius.
     *
     * @param radius how close something must be to the hook to be caught, in pixels.
     * @throws IllegalArgumentException if the radius is not positive.
     */
    public CatchDetector(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be positive");
        }
        this.radius = radius;
    }

    /**
     * {@return the first fish close enough to the hook, or empty if there is none}
     *
     * @param hook   the hook.
     * @param fishes the fishes in the lake.
     */
    public Optional<Fish> findCatch(Positioned hook, List<Fish> fishes) {
        Objects.requireNonNull(hook);
        Objects.requireNonNull(fishes);
        return fishes.stream()
                .filter(fish -> touches(hook, fish))
                .findFirst();
    }

    /**
     * {@return true if the target is close enough to the hook to be caught}
     *
     * @param hook   the hook.
     * @param target something in the water.
     */
    public boolean touches(Positioned hook, Positioned target) {
        Objects.requireNonNull(hook);
        Objects.requireNonNull(target);
        double distance = Math.hypot(target.getX() - hook.getX(), target.getY() - hook.getY());
        return distance <= radius;
    }
}