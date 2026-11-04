package it.unicam.cs.mpgc.rpg129382.model;

/**
 * Something whose position can be changed.
 */
public interface Movable extends Positioned {

    /**
     * Moves by the given offset.
     *
     * @param dx the horizontal offset, positive to the right.
     * @param dy the vertical offset, positive downwards.
     */
    void move(double dx, double dy);
}
