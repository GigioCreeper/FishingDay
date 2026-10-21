package it.unicam.cs.mpgc.rpg129382.model;

public enum Heading {
    LEFT(-1),
    RIGHT(1);

    private final int sign;

    Heading(int sign) {
        this.sign = sign;
    }

    public int sign() {
        return sign;
    }
}