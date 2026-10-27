package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingGame;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingGameFactory;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.view.navigation.GameScreen;
import it.unicam.cs.mpgc.rpg129382.view.navigation.NavigationShortcut;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenNavigator;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Objects;
import java.util.Random;

public final class FishingScreenFactory implements ScreenFactory {
    private static final KeyCode SHOP_KEY = KeyCode.B;

    private final Player player;
    private final ScreenNavigator navigator;

    public FishingScreenFactory(Player player, ScreenNavigator navigator) {
        this.player = Objects.requireNonNull(player);
        this.navigator = Objects.requireNonNull(navigator);
    }

    /**
     * Creates a new fishing session that uses the rod the player has right now.
     * @return the Parent node representing the fishing screen.
     */
    @Override
    public Parent createScreen() {
        FishingGame game = FishingGameFactory.create(player.getInventory(), player.getRod(), new Random());
        FishingView view = new FishingView(game, game, game);
        view.addEventHandler(KeyEvent.KEY_PRESSED, new NavigationShortcut(SHOP_KEY, navigator, GameScreen.SHOP));
        return view;
    }
}