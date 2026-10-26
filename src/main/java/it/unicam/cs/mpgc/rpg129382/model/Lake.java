package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Objects;

public record Lake(Area water, Area catchZone) {

    public Lake {
        Objects.requireNonNull(water);
        Objects.requireNonNull(catchZone);
    }
}
