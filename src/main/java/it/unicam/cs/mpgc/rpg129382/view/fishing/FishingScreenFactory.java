package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.controller.FishingController;
import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;
import it.unicam.cs.mpgc.rpg129382.model.Hook;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenFactory;
import javafx.scene.Parent;

public final class FishingScreenFactory implements ScreenFactory {

    @Override
    public Parent createScreen() {
        Hook hook = new Hook(GameConfig.WINDOW_WIDTH / 2.0, GameConfig.WINDOW_HEIGHT / 2.0);
        FishingGame game = new FishingGame(hook);
        FishingController controller = new FishingController(game);
        return new FishingView(game, controller);
    }
}