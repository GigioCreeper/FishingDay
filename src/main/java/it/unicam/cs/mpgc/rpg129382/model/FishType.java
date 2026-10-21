package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Objects;

public record FishType(String name, int value, double speed, double strength) {

    public FishType {
        Objects.requireNonNull(name);
        if (name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (value < 0) {
            throw new IllegalArgumentException("value must not be negative");
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be positive");
        }
        if (strength <= 0) {
            throw new IllegalArgumentException("strength must be positive");
        }
    }
}