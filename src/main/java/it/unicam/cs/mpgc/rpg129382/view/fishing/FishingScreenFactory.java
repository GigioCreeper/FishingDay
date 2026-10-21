package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.controller.FishingController;
import it.unicam.cs.mpgc.rpg129382.logic.CatchDetector;
import it.unicam.cs.mpgc.rpg129382.logic.CatchZone;
import it.unicam.cs.mpgc.rpg129382.logic.FishSpawner;
import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;
import it.unicam.cs.mpgc.rpg129382.model.FishType;
import it.unicam.cs.mpgc.rpg129382.model.Hook;
import it.unicam.cs.mpgc.rpg129382.model.Inventory;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenFactory;
import javafx.scene.Parent;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class FishingScreenFactory implements ScreenFactory {
    private final Inventory inventory;

    public FishingScreenFactory(Inventory inventory) {
        this.inventory = Objects.requireNonNull(inventory);
    }

    @Override
    public Parent createScreen() {
        Random random = new Random();
        Hook hook = new Hook(GameConfig.WINDOW_WIDTH / 2.0, GameConfig.WINDOW_HEIGHT / 2.0);
        List<FishType> types = List.of(
                new FishType("Persico", 5, 60, 50),
                new FishType("Trota", 10, 80, 80),
                new FishType("Luccio", 25, 110, 120));
        FishSpawner spawner = new FishSpawner(types, random);
        CatchDetector catchDetector = new CatchDetector(GameConfig.CATCH_RADIUS);
        CatchZone catchZone = new CatchZone(
                GameConfig.CATCH_ZONE_LEFT,
                GameConfig.CATCH_ZONE_RIGHT,
                GameConfig.WATER_TOP,
                GameConfig.CATCH_ZONE_BOTTOM);
        FishingGame game = new FishingGame(hook, spawner, catchDetector, catchZone, inventory, random);
        FishingController controller = new FishingController(game);
        return new FishingView(game, controller);
    }
}