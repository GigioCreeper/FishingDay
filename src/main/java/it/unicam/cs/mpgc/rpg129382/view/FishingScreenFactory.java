package it.unicam.cs.mpgc.rpg129382.view;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;
import it.unicam.cs.mpgc.rpg129382.model.Hook;
import javafx.scene.Parent;

public final class FishingScreenFactory implements ScreenFactory {

    @Override
    public Parent createScreen() {
        Hook hook = new Hook(GameConfig.WINDOW_WIDTH/2.0, GameConfig.WINDOW_HEIGHT/2.0);
        FishingGame game = new FishingGame(hook);
        return new FishingView(game);
    }

}
