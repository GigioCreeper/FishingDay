package it.unicam.cs.mpgc.rpg129382.view.navigation;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.view.window.LetterboxPane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A navigator that manages the transition between different game screens using a JavaFX Stage.
 * The stage keeps a single scene whose content is replaced on each navigation.
 */
public final class StageNavigator implements ScreenNavigator {
    private final LetterboxPane viewport = new LetterboxPane(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
    private final Map<GameScreen, ScreenFactory> screenFactories = new HashMap<>();

    /**
     * Creates a new StageNavigator and sets up the stage's scene.
     *
     * @param stage the stage to use for navigation.
     */
    public StageNavigator(Stage stage) {
        Objects.requireNonNull(stage)
                .setScene(new Scene(viewport, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT, Color.BLACK));
    }

    /**
     * Registers a game screen with the factory that creates it.
     *
     * @param screen  the game screen to register.
     * @param factory the factory that creates the screen's content.
     */
    public void register(GameScreen screen, ScreenFactory factory) {
        screenFactories.put(Objects.requireNonNull(screen), Objects.requireNonNull(factory));
    }

    /**
     * Navigates to the specified game screen by creating its content and showing it in the viewport.
     *
     * @param screen the game screen to navigate to.
     * @throws IllegalArgumentException if the screen is not registered.
     */
    @Override
    public void navigateTo(GameScreen screen) {
        ScreenFactory factory = screenFactories.get(screen);
        if (factory == null) {
            throw new IllegalArgumentException("Screen not registered: " + screen);
        }
        viewport.setContent(factory.createScreen());
    }
}