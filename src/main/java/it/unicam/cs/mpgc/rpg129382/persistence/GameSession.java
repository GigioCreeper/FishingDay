package it.unicam.cs.mpgc.rpg129382.persistence;

import it.unicam.cs.mpgc.rpg129382.logic.story.StoryProgress;
import it.unicam.cs.mpgc.rpg129382.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class GameSession {
    private final Player player;
    private final StoryProgress story;
    private final SaveStorage storage;
    private boolean started;

    public GameSession(Player player, StoryProgress story, SaveStorage storage) {
        this.player = Objects.requireNonNull(player);
        this.story = Objects.requireNonNull(story);
        this.storage = Objects.requireNonNull(storage);
    }

    public void save(){
        if(started){
            storage.save(snapshot());
        }
    }

    private SaveData snapshot() {
        Map<String, Integer> fishes = new HashMap<>();
        player.getInventory().getFishes()
                .forEach((type, count) -> fishes.put(type.name(), count));
        return new SaveData(player.getMoney(), player.getRod().name(), fishes,
                story.getChaptersRead(), story.isConcluded());
    }
}
