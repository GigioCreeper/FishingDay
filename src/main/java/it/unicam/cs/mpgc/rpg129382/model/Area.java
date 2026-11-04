package it.unicam.cs.mpgc.rpg129382.model;

/**
 * A rectangle in the game world, with its borders included.
 *
 * @param left   the x of the left border.
 * @param right  the x of the right border.
 * @param top    the y of the top border.
 * @param bottom the y of the bottom border.
 */
public record Area(double left, double right, double top, double bottom) {

    /**
     * @throws IllegalArgumentException if the area is empty or its borders are swapped.
     */
    public Area {
        if (left >= right || top >= bottom) {
            throw new IllegalArgumentException("invalid area bounds");
        }
    }

    /**
     * @param x the horizontal position.
     * @param y the vertical position.
     * @return true if the point is inside the area or on its borders.
     */
    public boolean contains(double x, double y) {
        return x >= left && x <= right && y >= top && y <= bottom;
    }

    /**
     * @param x a horizontal position.
     * @return the nearest horizontal position inside the area.
     */
    public double clampX(double x) {
        return Math.clamp(x, left, right);
    }

    /**
     * @param y a vertical position.
     * @return the nearest vertical position inside the area.
     */
    public double clampY(double y) {
        return Math.clamp(y, top, bottom);
    }
}
