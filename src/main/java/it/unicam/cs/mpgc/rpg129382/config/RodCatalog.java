package it.unicam.cs.mpgc.rpg129382.config;

import it.unicam.cs.mpgc.rpg129382.model.Rod;

import java.util.List;
import java.util.Optional;

/**
 * The rods the player can own, ordered from the starting one to the best one.
 */
public final class RodCatalog {
    public static final Rod BAMBOO = new Rod("bamboo", 0, 75);
    public static final Rod FIBERGLASS = new Rod("fiberglass", 120, 110);
    public static final Rod CARBON = new Rod("carbon", 350, 200);
    private static final List<Rod> LEVELS = List.of(BAMBOO, FIBERGLASS, CARBON);

    private RodCatalog() {}

    /**
     * @return the rod the player starts with.
     */
    public static Rod starter() {
        return LEVELS.getFirst();
    }

    /**
     * @param current the rod the player owns.
     * @return the next rod level, or empty if the current one is the best.
     * @throws IllegalArgumentException if the rod is not in the catalog.
     */
    public static Optional<Rod> nextAfter(Rod current) {
        int index = LEVELS.indexOf(current);
        if (index < 0) {
            throw new IllegalArgumentException("Unknown rod: " + current);
        }
        if (index + 1 >= LEVELS.size()) {
            return Optional.empty();
        }
        return Optional.of(LEVELS.get(index + 1));
    }
}