package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Inventory {
    private final Map<FishType, Integer> fishes = new LinkedHashMap<>();

    public void add(FishType type) {
        Objects.requireNonNull(type);
        fishes.merge(type, 1, Integer::sum);
    }

    public int count(FishType type) {
        return fishes.getOrDefault(type, 0);
    }

    public int totalCount() {
        return fishes.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int totalValue() {
        return fishes.entrySet().stream()
                .mapToInt(entry -> entry.getKey().value() * entry.getValue())
                .sum();
    }

    public Map<FishType, Integer> getFishes() {
        return Collections.unmodifiableMap(fishes);
    }

    public void clear() {
        fishes.clear();
    }
}