package it.unicam.cs.mpgc.rpg129382.logic;

public record CatchZone(double left, double right,double top, double bottom) {
    public CatchZone{
        if(left >= right || top >= bottom){
            throw new IllegalArgumentException("Invalid catch zone coordinates");
        }
    }

    public boolean contains(double x, double y){
        return x >= left && x <= right && y >= top && y <= bottom;
    }
}
