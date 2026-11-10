package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.model.Area;
import it.unicam.cs.mpgc.rpg129382.model.Heading;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;

import java.util.Objects;

/**
 * The left and right sides of an area, where things enter and leave it.
 *
 * @param area   the area things move across.
 * @param margin how far outside the area things enter and leave.
 */
public record LakeEdges(Area area, double margin) {

    /**
     * Creates the edges of the given area.
     *
     * @throws IllegalArgumentException if the margin is negative.
     */
    public LakeEdges {
        Objects.requireNonNull(area);
        if (margin < 0) {
            throw new IllegalArgumentException("margin must not be negative");
        }
    }

    /**
     * {@return the horizontal position, just outside the area, where it must enter to move that way}
     *
     * @param heading the direction something will move to.
     */
    double entryX(Heading heading) {
        return heading == Heading.RIGHT
                ? area.left() - margin
                : area.right() + margin;
    }

    /**
     * {@return true if it is farther than the margin from one of the sides}
     *
     * @param thing something moving across the area.
     */
    boolean hasLeft(Positioned thing) {
        return thing.getX() < area.left() - margin
                || thing.getX() > area.right() + margin;
    }
}
