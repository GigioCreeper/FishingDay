package it.unicam.cs.mpgc.rpg129382.logic.fishing.fight;

import it.unicam.cs.mpgc.rpg129382.model.Fish;

@FunctionalInterface
public interface FightFactory {

    Fight create(Fish fish);
}