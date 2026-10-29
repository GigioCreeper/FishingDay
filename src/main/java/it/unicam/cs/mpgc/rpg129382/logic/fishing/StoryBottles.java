package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.fishing.fight.DurationRange;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryMessage;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryProgress;
import it.unicam.cs.mpgc.rpg129382.model.Area;
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
    private final Area water;
    private final double surfaceY;
    private final double margin;
    private final double speed;
    private final DurationRange delays;
    private final Random random;
    private MessageBottle bottle;
    private double cooldown;

    /**
     * @param story the story the bottles carry.
     * @param water the water the bottles drift across.
     * @param surfaceY the height at which the bottles float.
     * @param margin how far outside the water bottles appear and disappear.
     * @param speed how fast the bottles drift.
     * @param delays how long to wait before a new bottle appears.
     * @param random the source of randomness.
     */
    public StoryBottles(StoryProgress story, Area water, double surfaceY, double margin, double speed,
                        DurationRange delays, Random random) {
        if (margin < 0) {
            throw new IllegalArgumentException("margin must not be negative");
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be positive");
        }
        this.story = Objects.requireNonNull(story);
        this.water = Objects.requireNonNull(water);
        this.surfaceY = surfaceY;
        this.margin = margin;
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
            if (hasLeft(bottle)) {
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
        Heading heading = random.nextBoolean() ? Heading.RIGHT : Heading.LEFT;
        double x = heading == Heading.RIGHT ? water.left() - margin : water.right() + margin;
        return new MessageBottle(heading, speed, x, surfaceY);
    }

    private boolean hasLeft(MessageBottle bottle) {
        return bottle.getX() < water.left() - margin || bottle.getX() > water.right() + margin;
    }

    private void removeBottle() {
        bottle = null;
        cooldown = delays.next(random);
    }
}