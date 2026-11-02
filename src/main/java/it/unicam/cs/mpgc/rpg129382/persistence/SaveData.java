package it.unicam.cs.mpgc.rpg129382.persistence;

import java.util.Map;
import java.util.Objects;

public record SaveData(int money, String rodName, Map<String, Integer> fishes, int chaptersRead, boolean storyCompleted) {
    public SaveData {
        Objects.requireNonNull(rodName);
        fishes = Map.copyOf(fishes);
        if (money < 0) throw new IllegalArgumentException("Money cannot be negative");
        if (chaptersRead < 0) throw new IllegalArgumentException("Chapters read cannot be negative");
        if (fishes.values().stream().anyMatch(count -> count <= 0)) {
            throw new IllegalArgumentException("Fish counts must be positive");
        }
    }
}
