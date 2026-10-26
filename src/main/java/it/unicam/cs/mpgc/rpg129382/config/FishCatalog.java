package it.unicam.cs.mpgc.rpg129382.config;

import it.unicam.cs.mpgc.rpg129382.model.FishType;

import java.util.List;

public final class FishCatalog {
    private static final List<FishType> TYPES = List.of(
            new FishType("perch", 5, 60, 50),
            new FishType("trout", 10, 80, 80),
            new FishType("pike", 25, 110, 120),
            new FishType("bass", 30, 100, 100),
            new FishType("chub", 15, 90, 70));

    private FishCatalog() {
    }

    public static List<FishType> all() {
        return TYPES;
    }
}