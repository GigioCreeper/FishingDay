package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.FishCatalog;
import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.controller.FishingController;
import it.unicam.cs.mpgc.rpg129382.logic.Area;
import it.unicam.cs.mpgc.rpg129382.logic.CatchDetector;
import it.unicam.cs.mpgc.rpg129382.logic.FishPopulation;
import it.unicam.cs.mpgc.rpg129382.logic.FishSpawner;
import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;
import it.unicam.cs.mpgc.rpg129382.logic.Lake;
import it.unicam.cs.mpgc.rpg129382.model.Hook;
import it.unicam.cs.mpgc.rpg129382.model.Inventory;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenFactory;
import javafx.scene.Parent;

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
        FishPopulation population = new FishPopulation(new FishSpawner(FishCatalog.all(), random));
        CatchDetector catchDetector = new CatchDetector(GameConfig.CATCH_RADIUS);
        Area water = new Area(0, GameConfig.WINDOW_WIDTH, GameConfig.WATER_TOP, GameConfig.WINDOW_HEIGHT);
        Area catchZone = new Area(
                GameConfig.CATCH_ZONE_LEFT,
                GameConfig.CATCH_ZONE_RIGHT,
                GameConfig.WATER_TOP,
                GameConfig.CATCH_ZONE_BOTTOM);
        Lake lake = new Lake(water, catchZone);
        FishingGame game = new FishingGame(hook, population, catchDetector, lake, inventory, random);
        FishingController controller = new FishingController(game);
        return new FishingView(game, controller);
    }
}