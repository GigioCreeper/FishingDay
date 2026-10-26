package it.unicam.cs.mpgc.rpg129382.config;

import it.unicam.cs.mpgc.rpg129382.model.FishType;

import java.util.List;

public final class FishCatalog {
    private static final List<FishType> TYPES = List.of(
            new FishType("Persico", 5, 60, 50),
            new FishType("Trota", 10, 80, 80),
            new FishType("Luccio", 25, 110, 120));

    private FishCatalog() {
    }

    public static List<FishType> all() {
        return TYPES;
    }
}