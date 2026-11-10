package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingGame;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingGameFactory;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryProgress;
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

/**
 * Creates the fishing screen: a new fishing session every time the player goes fishing.
 * From there, N opens the shop and Esc goes back to the main menu.
 */
public final class FishingScreenFactory implements ScreenFactory {
    private static final KeyCode SHOP_KEY = KeyCode.N;
    private static final KeyCode MENU_KEY = KeyCode.ESCAPE;

    private final Player player;
    private final StoryProgress story;
    private final ScreenNavigator navigator;

    /**
     * Creates a factory of fishing screens.
     *
     * @param player    the player who fishes.
     * @param story     the story carried by the bottles.
     * @param navigator the navigator used to leave the screen.
     */
    public FishingScreenFactory(Player player, StoryProgress story, ScreenNavigator navigator) {
        this.player = Objects.requireNonNull(player);
        this.story = Objects.requireNonNull(story);
        this.navigator = Objects.requireNonNull(navigator);
    }

    /**
     * Creates a new fishing session that uses the rod the player has right now.
     *
     * @return the Parent node representing the fishing screen.
     */
    @Override
    public Parent createScreen() {
        FishingGame game = FishingGameFactory.create(player, story, new Random());
        FishingView view = new FishingView(game, game, game, () -> navigator.navigateTo(GameScreen.MAIN_MENU));
        view.addEventHandler(KeyEvent.KEY_PRESSED, new NavigationShortcut(SHOP_KEY, navigator, GameScreen.SHOP));
        view.addEventHandler(KeyEvent.KEY_PRESSED, new NavigationShortcut(MENU_KEY, navigator, GameScreen.MAIN_MENU));
        return view;
    }
}