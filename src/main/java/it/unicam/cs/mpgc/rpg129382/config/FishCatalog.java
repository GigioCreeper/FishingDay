package it.unicam.cs.mpgc.rpg129382.config;

import it.unicam.cs.mpgc.rpg129382.model.FishType;

import java.util.List;
import java.util.Optional;

public final class FishCatalog {
    public static final FishType PERCH = new FishType("perch", 5, 60, 50);
    public static final FishType TROUT = new FishType("trout", 10, 80, 80);
    public static final FishType PIKE = new FishType("pike", 25, 110, 120);
    public static final FishType BASS = new FishType("bass", 30, 100, 100);
    public static final FishType CHUB = new FishType("chub", 15, 90, 70);

    /**
     * The fish the story is about. It is not among the common types: only the story makes it appear.
     */
    // Tuned so that, with the best rod, even a flawless fight ends with the line broken about half of the time.
    public static final FishType LEGEND = new FishType("legend", 0, 45, 155);

    private static final List<FishType> TYPES = List.of(PERCH, TROUT, PIKE, BASS, CHUB);

    private FishCatalog() {
    }

    public static List<FishType> all() {
        return TYPES;
    }

    public static Optional<FishType> findByName(String name) {
        return TYPES.stream()
                .filter(type -> type.name().equals(name))
                .findFirst();
    }
}