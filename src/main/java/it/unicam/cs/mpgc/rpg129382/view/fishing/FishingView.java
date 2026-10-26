package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.logic.FishingCommands;
import it.unicam.cs.mpgc.rpg129382.logic.FishingState;
import it.unicam.cs.mpgc.rpg129382.logic.Updatable;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class FishingView extends Pane {
    private static final boolean SHOW_CATCH_ZONE = true;

    private final Canvas canvas;
    private final FishingRenderer renderer;
    private final Updatable game;
    private final AnimationTimer timer;

    public FishingView(FishingState state, FishingCommands commands, Updatable game) {
        this.game = Objects.requireNonNull(game);
        this.canvas = new Canvas(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        this.renderer = new FishingRenderer(Objects.requireNonNull(state), SHOW_CATCH_ZONE);
        this.timer = createTimer();
        this.getChildren().add(canvas);
        setupInput(new FishingKeyboardInput(commands));
        draw();
    }

    private AnimationTimer createTimer() {
        return new AnimationTimer() {
            private long lastTime;

            @Override
            public void handle(long now) {
                if (lastTime != 0) {
                    game.update((now - lastTime) / 1_000_000_000.0);
                }
                lastTime = now;
                draw();
            }
        };
    }

    private void setupInput(FishingKeyboardInput input) {
        setFocusTraversable(true);
        setOnKeyPressed(input::onKeyPressed);
        setOnKeyReleased(input::onKeyReleased);
        sceneProperty().addListener((_, _, newScene) -> {
            if (newScene != null) {
                requestFocus();
                timer.start();
            } else {
                timer.stop();
            }
        });
    }

    private void draw() {
        renderer.draw(canvas.getGraphicsContext2D());
    }
}
