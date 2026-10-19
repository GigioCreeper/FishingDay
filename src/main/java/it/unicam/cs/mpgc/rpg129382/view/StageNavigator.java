package it.unicam.cs.mpgc.rpg129382.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class StageNavigator implements ScreenNavigator {
    private final Stage stage;
    private final Map<GameScreen, Supplier<Parent>> screenLoaders = new HashMap<>();

    public StageNavigator(Stage stage) {
        this.stage = Objects.requireNonNull(stage);
    }

    public void register(GameScreen screen, Supplier<Parent> loader) {
        screenLoaders.put(Objects.requireNonNull(screen), Objects.requireNonNull(loader));
    }

    @Override
    public void navigateTo(GameScreen screen) {
        Supplier<Parent> loader = screenLoaders.get(screen);
        if (loader == null) {
            throw new IllegalArgumentException("Screen not registered: " + screen);
        }
        stage.setScene(new Scene(loader.get(), 800, 600));
    }
}