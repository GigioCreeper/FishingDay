package it.unicam.cs.mpgc.rpg129382.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A navigator that manages the transition between different game screens using a JavaFX Stage.
 */
public final class StageNavigator implements ScreenNavigator {
    private final Stage stage;
    private final Map<GameScreen, ScreenFactory> screenFactories = new HashMap<>();

    /**
     * Creates a new StageNavigator with the specified stage.
     * @param stage the stage to use for navigation.
     */
    public StageNavigator(Stage stage) {
        this.stage = Objects.requireNonNull(stage);
    }

    /**
     * Registers a game screen with the factory that creates it.
     * @param screen the game screen to register.
     * @param factory the factory that creates the screen's content.
     */
    public void register(GameScreen screen, ScreenFactory factory) {
        screenFactories.put(Objects.requireNonNull(screen), Objects.requireNonNull(factory));
    }

    /**
     * Navigates to the specified game screen by creating its content and showing it on the stage.
     * @param screen the game screen to navigate to.
     * @throws IllegalArgumentException if the screen is not registered.
     */
    @Override
    public void navigateTo(GameScreen screen) {
        ScreenFactory factory = screenFactories.get(screen);
        if (factory == null) {
            throw new IllegalArgumentException("Screen not registered: " + screen);
        }
        stage.setScene(new Scene(factory.createScreen(), 800, 600));
    }
}