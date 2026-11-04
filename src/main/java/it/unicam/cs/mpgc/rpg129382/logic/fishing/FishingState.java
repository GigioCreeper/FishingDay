package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.story.StoryMessage;
import it.unicam.cs.mpgc.rpg129382.model.Area;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;
import it.unicam.cs.mpgc.rpg129382.model.ReadOnlyFish;

import java.util.List;
import java.util.Optional;

/**
 * Read-only view of a fishing session, used by the view to draw it.
 */
public interface FishingState {

    /**
     * @return the hook.
     */
    Positioned getHook();

    /**
     * @return the fishes in the lake, the hooked one included.
     */
    List<? extends ReadOnlyFish> getFishes();

    /**
     * @return the zone where a hooked fish must be brought to be caught.
     */
    Area getCatchZone();

    /**
     * @return true if a fish is on the hook.
     */
    boolean hasHookedFish();

    /**
     * @return true if a hooked fish is pulling and the player cannot move the hook.
     */
    boolean isFishPulling();

    /**
     * @return how many fishes the player has caught and not sold yet.
     */
    int getInventoryCount();

    /**
     * @return the coins the player owns.
     */
    int getMoney();

    /**
     * @return the bottle drifting on the lake, if any.
     */
    Optional<? extends Positioned> getBottle();

    /**
     * @return the piece of story the player is reading; while present, the session is paused.
     */
    Optional<StoryMessage> getStoryMessage();
}