package it.unicam.cs.mpgc.rpg129382.logic.fishing;

import it.unicam.cs.mpgc.rpg129382.model.Area;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;
import it.unicam.cs.mpgc.rpg129382.model.ReadOnlyFish;

import java.util.List;

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
}
