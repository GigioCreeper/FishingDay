package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.model.Area;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;
import it.unicam.cs.mpgc.rpg129382.model.ReadOnlyFish;

import java.util.List;
import java.util.Optional;

/**
 * Read-only view of a fishing session, used by the view to draw it.
 */
public interface FishingState {

    Positioned getHook();

    List<? extends ReadOnlyFish> getFishes();

    Area getCatchZone();

    boolean hasHookedFish();

    boolean isFishPulling();

    int getInventoryCount();

    int getMoney();

    /**
     * @return the bottle drifting on the lake, if any.
     */
    Optional<? extends Positioned> getBottle();

    /**
     * @return the message of the bottle the player is reading; while present, the session is paused.
     */
    Optional<String> getStoryMessage();
}