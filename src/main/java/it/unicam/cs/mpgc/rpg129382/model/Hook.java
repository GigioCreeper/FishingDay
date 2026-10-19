package it.unicam.cs.mpgc.rpg129382.model;

public class Hook implements Movable{
    private double x;
    private double y;

    public Hook(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void move(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
}

