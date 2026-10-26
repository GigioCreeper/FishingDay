package it.unicam.cs.mpgc.rpg129382.model;

public record Area(double left, double right, double top, double bottom) {

    public Area {
        if (left >= right || top >= bottom) {
            throw new IllegalArgumentException("invalid area bounds");
        }
    }

    public boolean contains(double x, double y) {
        return x >= left && x <= right && y >= top && y <= bottom;
    }

    public double clampX(double x) {
        return Math.clamp(x, left, right);
    }

    public double clampY(double y) {
        return Math.clamp(y, top, bottom);
    }
}
