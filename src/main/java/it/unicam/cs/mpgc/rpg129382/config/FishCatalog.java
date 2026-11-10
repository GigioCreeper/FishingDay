package it.unicam.cs.mpgc.rpg129382.config;

import it.unicam.cs.mpgc.rpg129382.model.FishType;

import java.util.List;
import java.util.Optional;

/**
 * The types of fish of the game and their characteristics.
 */
public final class FishCatalog {
    /** Small and slow: the easiest catch. */
    public static final FishType PERCH = new FishType("perch", 5, 60, 50);
    /** A common fish of medium strength. */
    public static final FishType TROUT = new FishType("trout", 10, 80, 80);
    /** Fast and strong: hard to hold with the starting rod. */
    public static final FishType PIKE = new FishType("pike", 25, 110, 120);
    /** The most valuable of the common fish. */
    public static final FishType BASS = new FishType("bass", 30, 100, 100);
    /** A fish of medium value and strength. */
    public static final FishType CHUB = new FishType("chub", 15, 90, 70);

    /**
     * The fish the story is about. It is not among the common types: only the story makes it appear.
     * Its strength is tuned so that, with the best rod, even a flawless fight breaks the line about half of the time.
     */
    public static final FishType LEGEND = new FishType("legend", 0, 45, 155);

    private static final List<FishType> TYPES = List.of(PERCH, TROUT, PIKE, BASS, CHUB);

    private FishCatalog() {
    }

    /**
     * {@return the common types of fish, without the final one}
     */
    public static List<FishType> all() {
        return TYPES;
    }

    /**
     * {@return the common type with that name, or empty if the catalog has none}
     *
     * @param name the name of a type of fish.
     */
    public static Optional<FishType> findByName(String name) {
        return TYPES.stream()
                .filter(type -> type.name().equals(name))
                .findFirst();
    }
}