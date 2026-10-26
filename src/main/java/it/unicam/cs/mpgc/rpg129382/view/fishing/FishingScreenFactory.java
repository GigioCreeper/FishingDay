package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;
import it.unicam.cs.mpgc.rpg129382.logic.FishingGameFactory;
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
        FishingGame game = FishingGameFactory.create(inventory, new Random());
        return new FishingView(game, game, game);
    }
}
