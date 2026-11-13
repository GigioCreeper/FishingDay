package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The fish the player has caught and not sold yet, counted by type.
 */
public class Inventory {
    private final Map<FishType, Integer> fishes = new HashMap<>();

    /**
     * Creates an empty inventory.
     */
    public Inventory() {
    }

    /**
     * Adds one fish.
     *
     * @param type the type of the fish.
     */
    public void add(FishType type) {
        add(type, 1);
    }

    /**
     * Adds several fish of the same type.
     *
     * @param type  the type of the fish.
     * @param count how many fish to add.
     * @throws IllegalArgumentException if the count is not positive.
     */
    public void add(FishType type, int count) {
        Objects.requireNonNull(type);
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive");
        }
        fishes.merge(type, count, Integer::sum);
    }

    /**
     * {@return how many fish there are, of every type}
     */
    public int totalCount() {
        return fishes.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * {@return the coins all the fish are worth together}
     */
    public int totalValue() {
        return fishes.entrySet().stream().mapToInt(entry -> entry.getKey().value() * entry.getValue()).sum();
    }

    /**
     * {@return how many fish there are of each type; it cannot be modified}
     */
    public Map<FishType, Integer> getFishes() {
        return Collections.unmodifiableMap(fishes);
    }

    /**
     * Removes every fish.
     */
    public void clear() {
        fishes.clear();
    }
}