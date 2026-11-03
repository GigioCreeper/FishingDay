package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.DurationRange;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryMessage;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryProgress;
import it.unicam.cs.mpgc.rpg129382.model.Heading;
import it.unicam.cs.mpgc.rpg129382.model.MessageBottle;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Lets a bottle drift across the lake whenever the story has an unread chapter.
 * There is at most one bottle at a time; if it drifts away, another one comes later.
 */
public final class StoryBottles {
    private final StoryProgress story;
    private final LakeEdges edges;
    private final double surfaceY;
    private final double speed;
    private final DurationRange delays;
    private final Random random;
    private MessageBottle bottle;
    private double cooldown;

    /**
     * @param story the story to be read from the bottles.
     * @param edges the lake edges, used to spawn and remove bottles.
     * @param surfaceY the vertical position of the water surface.
     * @param speed the speed at which the bottle drifts across the lake.
     * @param delays the range of time between two consecutive bottles.
     * @param random a source of randomness.
     */
    public StoryBottles(StoryProgress story, LakeEdges edges, double surfaceY, double speed,
                        DurationRange delays, Random random) {
        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be positive");
        }
        this.story = Objects.requireNonNull(story);
        this.edges = Objects.requireNonNull(edges);
        this.surfaceY = surfaceY;
        this.speed = speed;
        this.delays = Objects.requireNonNull(delays);
        this.random = Objects.requireNonNull(random);
        this.cooldown = delays.next(random);
    }

    /**
     * @return the bottle drifting on the lake, if any.
     */
    public Optional<MessageBottle> getBottle() {
        return Optional.ofNullable(bottle);
    }

    public void update(double delta) {
        if (bottle != null) {
            bottle.drift(delta);
            if (edges.hasLeft(bottle)) {
                removeBottle();
            }
            return;
        }
        if (story.hasUnreadChapter()) {
            waitForNextBottle(delta);
        }
    }

    private void waitForNextBottle(double delta) {
        cooldown -= delta;
        if (cooldown <= 0) {
            bottle = spawn();
        }
    }

    /**
     * Takes the bottle out of the water and reads its message.
     * @return the message carrying the chapter of the story found in the bottle.
     * @throws IllegalStateException if there is no bottle on the lake.
     */
    public StoryMessage pickUp() {
        if (bottle == null) {
            throw new IllegalStateException("no bottle to pick up");
        }
        removeBottle();
        return new StoryMessage(StoryMessage.Kind.BOTTLE, story.readNextChapter());
    }

    private MessageBottle spawn() {
        Heading heading = Heading.random(random);
        double x = edges.entryX(heading);
        return new MessageBottle(heading, speed, x, surfaceY);
    }

    private void removeBottle() {
        bottle = null;
        cooldown = delays.next(random);
    }
}