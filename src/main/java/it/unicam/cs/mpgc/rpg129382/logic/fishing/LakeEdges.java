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

    public LakeEdges {
        Objects.requireNonNull(area);
        if (margin < 0) {
            throw new IllegalArgumentException("margin must not be negative");
        }
    }

    double entryX(Heading heading) {
        return heading == Heading.RIGHT
                ? area.left() - margin
                : area.right() + margin;
    }

    boolean hasLeft(Positioned thing) {
        return thing.getX() < area.left() - margin
                || thing.getX() > area.right() + margin;
    }
}
