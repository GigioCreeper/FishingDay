package it.unicam.cs.mpgc.rpg129382;

import it.unicam.cs.mpgc.rpg129382.config.RodCatalog;
import it.unicam.cs.mpgc.rpg129382.logic.session.GameSession;
import it.unicam.cs.mpgc.rpg129382.logic.shop.Shop;
import it.unicam.cs.mpgc.rpg129382.logic.story.ResourceStoryBook;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryProgress;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.persistence.JsonSaveStorage;
import it.unicam.cs.mpgc.rpg129382.view.fishing.FishingScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.menu.MainMenuScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.navigation.GameScreen;
import it.unicam.cs.mpgc.rpg129382.view.navigation.StageNavigator;
import it.unicam.cs.mpgc.rpg129382.view.shop.ShopScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.window.FullScreenToggle;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.nio.file.Path;

/**
 * The JavaFX application: builds the objects of the game, connects the screens
 * and saves the game when the window closes.
 */
public class FishingDayApp extends Application {
    private static final Path SAVE_FILE = Path.of(System.getProperty("user.home"), ".fishingday", "save.json");

    private GameSession session;

    /**
     * Creates the application; JavaFX calls it before {@link #start(Stage)}.
     */
    public FishingDayApp() {
    }

    @Override
    public void start(Stage stage) {
        StageNavigator navigator = new StageNavigator(stage);
        Player player = new Player(RodCatalog.starter());
        Shop shop = new Shop();
        StoryProgress story = new StoryProgress(ResourceStoryBook.load("story"),
                () -> RodCatalog.levelOf(player.getRod()) + 1);
        session = new GameSession(player, story, new JsonSaveStorage(SAVE_FILE));
        MainMenuScreenFactory menu = new MainMenuScreenFactory(navigator, session);
        navigator.register(GameScreen.MAIN_MENU, () -> {
            session.save();
            return menu.createScreen();
        });
        navigator.register(GameScreen.FISHING, new FishingScreenFactory(player, story, navigator));
        navigator.register(GameScreen.SHOP, new ShopScreenFactory(shop, player, navigator));

        stage.setTitle("Fishing Day");
        stage.setResizable(false);
        stage.addEventFilter(KeyEvent.KEY_PRESSED, new FullScreenToggle(stage, KeyCode.F11));
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        navigator.navigateTo(GameScreen.MAIN_MENU);
        stage.show();
    }

    @Override
    public void stop() {
        if (session != null) {
            session.save();
        }
    }
}