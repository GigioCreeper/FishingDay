package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Inventory {
    private final Map<FishType, Integer> fishes = new LinkedHashMap<>();

    public void add(FishType type) {
        add(type, 1);
    }

    public void add(FishType type, int count) {
        Objects.requireNonNull(type);
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive");
        }
        fishes.merge(type, count, Integer::sum);
    }


    public int totalCount() {
        return fishes.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int totalValue() {
        return fishes.entrySet().stream().mapToInt(entry -> entry.getKey().value() * entry.getValue()).sum();
    }

    public Map<FishType, Integer> getFishes() {
        return Collections.unmodifiableMap(fishes);
    }

    public void clear() {
        fishes.clear();
    }
}