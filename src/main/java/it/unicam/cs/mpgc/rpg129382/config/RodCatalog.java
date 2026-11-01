package it.unicam.cs.mpgc.rpg129382.config;

import it.unicam.cs.mpgc.rpg129382.model.Rod;

import java.util.List;
import java.util.Optional;

/**
 * The rods the player can own, ordered from the starting one to the best one.
 */
public final class RodCatalog {
    public static final Rod BAMBOO = new Rod("Bamboo", 0, 75);
    public static final Rod FIBERGLASS = new Rod("Fiberglass", 120, 110);
    public static final Rod CARBON = new Rod("Carbon", 350, 200);

    private static final List<Rod> LEVELS = List.of(BAMBOO, FIBERGLASS, CARBON);

    private RodCatalog() {}

    /**
     * @return every rod, from the starting one to the best one.
     */
    public static List<Rod> all() {
        return LEVELS;
    }

    /**
     * @return the rod the player starts with.
     */
    public static Rod starter() {
        return LEVELS.getFirst();
    }

    /**
     * @param rod a rod of the catalog.
     * @return its level, starting from 0 for the starting rod.
     * @throws IllegalArgumentException if the rod is not in the catalog.
     */
    public static int levelOf(Rod rod) {
        int index = LEVELS.indexOf(rod);
        if (index < 0) {
            throw new IllegalArgumentException("Unknown rod: " + rod);
        }
        return index;
    }

    /**
     * @param current the rod the player owns.
     * @return the next rod level, or empty if the current one is the best.
     * @throws IllegalArgumentException if the rod is not in the catalog.
     */
    public static Optional<Rod> nextAfter(Rod current) {
        int next = levelOf(current) + 1;
        if (next >= LEVELS.size()) {
            return Optional.empty();
        }
        return Optional.of(LEVELS.get(next));
    }
}