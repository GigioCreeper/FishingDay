package it.unicam.cs.mpgc.rpg129382.model;


/**
 * Represents an object that can be moved in a 2D space.
 */
public interface Movable {
    double getX();
    double getY();

    /**
     * Moves the object by the specified delta values in the x and y directions.
     * @param deltaX the change in the x-coordinate
     * @param deltaY the change in the y-coordinate
     */
    void move(double deltaX, double deltaY);
}
