package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Objects;

public record FishType(String name, int value, double speed) {
    public FishType{
        Objects.requireNonNull(name);
        if (value < 0 || speed < 0) {
            throw new IllegalArgumentException("Value and speed must be non-negative");
        }
    }
}
