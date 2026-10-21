package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Controllable;

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

    public Optional<Fish> findCatch(Controllable hook, List<Fish> fishes) {
        Objects.requireNonNull(hook);
        Objects.requireNonNull(fishes);
        return fishes.stream()
                .filter(fish -> isTouching(hook, fish))
                .findFirst();
    }

    private boolean isTouching(Controllable hook, Fish fish) {
        double distance = Math.hypot(fish.getX() - hook.getX(), fish.getY() - hook.getY());
        return distance <= radius;
    }
}