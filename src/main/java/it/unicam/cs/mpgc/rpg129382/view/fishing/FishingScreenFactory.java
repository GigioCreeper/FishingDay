package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingGame;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingGameFactory;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenFactory;
import javafx.scene.Parent;

import java.util.Objects;
import java.util.Random;

public final class FishingScreenFactory implements ScreenFactory {
    private final Player player;

    public FishingScreenFactory(Player player) {
        this.player = Objects.requireNonNull(player);
    }

    /**
     * Creates a new fishing session that uses the rod the player has right now.
     * @return the Parent node representing the fishing screen.
     */
    @Override
    public Parent createScreen() {
        FishingGame game = FishingGameFactory.create(player.getInventory(), player.getRod(), new Random());
        return new FishingView(game, game, game);
    }
}