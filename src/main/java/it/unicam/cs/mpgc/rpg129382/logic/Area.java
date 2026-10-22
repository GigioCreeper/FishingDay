package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.model.Controllable;

public record Area(double left, double right, double top, double bottom) {

    public Area {
        if (left >= right || top >= bottom) {
            throw new IllegalArgumentException("invalid area bounds");
        }
    }

    public boolean contains(double x, double y) {
        return x >= left && x <= right && y >= top && y <= bottom;
    }

    public void keepInside(Controllable object) {
        double x = Math.clamp(object.getX(), left, right);
        double y = Math.clamp(object.getY(), top, bottom);
        object.move(x - object.getX(), y - object.getY());
    }
}