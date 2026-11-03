package it.unicam.cs.mpgc.rpg129382.logic.session;

import it.unicam.cs.mpgc.rpg129382.config.FishCatalog;
import it.unicam.cs.mpgc.rpg129382.config.RodCatalog;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryProgress;
import it.unicam.cs.mpgc.rpg129382.model.FishType;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.persistence.SaveData;
import it.unicam.cs.mpgc.rpg129382.persistence.SaveStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

    public void newGame() {
        player.restore(RodCatalog.starter(), 0, Map.of());
        story.restore(0, false);
        started = true;
    }

    public boolean canContinue() {
        return continuableSave().isPresent();
    }


    public void continueGame() {
        SaveData save = continuableSave()
                .orElseThrow(() -> new IllegalStateException("No game to continue"));
        Map<FishType, Integer> fishes = new HashMap<>();
        save.fishes().forEach((name, count) -> fishes.put(FishCatalog.findByName(name).orElseThrow(), count));
        player.restore(RodCatalog.findByName(save.rodName()).orElseThrow(), save.money(), fishes);
        story.restore(save.chaptersRead(), save.storyCompleted());
        started = true;
    }

    private Optional<SaveData> continuableSave() {
        return storage.load().filter(save -> !save.storyCompleted() && isValid(save));
    }

    private boolean isValid(SaveData save) {
        return RodCatalog.findByName(save.rodName()).isPresent()
                && save.chaptersRead() <= story.chapterCount()
                && save.fishes().keySet().stream()
                .allMatch(name -> FishCatalog.findByName(name).isPresent());
    }

    private SaveData snapshot() {
        Map<String, Integer> fishes = new HashMap<>();
        player.getInventory().getFishes()
                .forEach((type, count) -> fishes.put(type.name(), count));
        return new SaveData(player.getMoney(), player.getRod().name(), fishes,
                story.getChaptersRead(), story.isConcluded());
    }
}
