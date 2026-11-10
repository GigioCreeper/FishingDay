package it.unicam.cs.mpgc.rpg129382.logic.fishing.fight;

import it.unicam.cs.mpgc.rpg129382.model.Fish;

/**
 * Creates the fight that starts when a fish gets hooked.
 */
@FunctionalInterface
public interface FightFactory {

    /**
     * {@return a new fight with that fish}
     *
     * @param fish the fish that has just been hooked.
     */
    Fight create(Fish fish);
}