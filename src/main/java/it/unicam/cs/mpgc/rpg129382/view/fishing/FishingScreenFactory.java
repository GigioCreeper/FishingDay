package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.controller.FishingController;
import it.unicam.cs.mpgc.rpg129382.logic.CatchDetector;
import it.unicam.cs.mpgc.rpg129382.logic.FishSpawner;
import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;
import it.unicam.cs.mpgc.rpg129382.model.FishType;
import it.unicam.cs.mpgc.rpg129382.model.Hook;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenFactory;
import javafx.scene.Parent;

import java.util.List;
import java.util.Random;

public final class FishingScreenFactory implements ScreenFactory {

    @Override
    public Parent createScreen() {
        Hook hook = new Hook(GameConfig.WINDOW_WIDTH / 2.0, GameConfig.WINDOW_HEIGHT / 2.0);
        FishSpawner spawner = new FishSpawner(List.of(new FishType("Trota", 10, 80)), new Random());
        FishingGame game = new FishingGame(hook, spawner, new CatchDetector(GameConfig.CATCH_RADIUS));
        FishingController controller = new FishingController(game);
        return new FishingView(game, controller);
    }
}