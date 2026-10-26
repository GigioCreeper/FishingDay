package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;

import java.util.List;

/**
 * Read-only view of a fishing session, used by the view to draw it.
 */
public interface FishingState {

    Positioned getHook();

    List<Fish> getFishes();

    Area getCatchZone();

    boolean hasHookedFish();

    boolean isFishPulling();

    int getInventoryCount();
}