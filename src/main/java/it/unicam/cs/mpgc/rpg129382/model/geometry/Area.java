package it.unicam.cs.mpgc.rpg129382.model.geometry;

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
     * Creates an area, checking that its borders are in the right order.
     *
     * @throws IllegalArgumentException if the area is empty or its borders are swapped.
     */
    public Area {
        if (left >= right || top >= bottom) {
            throw new IllegalArgumentException("invalid area bounds");
        }
    }

    /**
     * {@return true if the point is inside the area or on its borders}
     *
     * @param x the horizontal position.
     * @param y the vertical position.
     */
    public boolean contains(double x, double y) {
        return x >= left && x <= right && y >= top && y <= bottom;
    }

    /**
     * {@return the nearest horizontal position inside the area}
     *
     * @param x a horizontal position.
     */
    public double clampX(double x) {
        return Math.clamp(x, left, right);
    }

    /**
     * {@return the nearest vertical position inside the area}
     *
     * @param y a vertical position.
     */
    public double clampY(double y) {
        return Math.clamp(y, top, bottom);
    }
}
