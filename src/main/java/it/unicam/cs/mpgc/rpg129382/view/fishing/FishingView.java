package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.controller.FishingController;
import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class FishingView extends Pane {
    private final Canvas canvas;
    private final FishingRenderer renderer;
    private final FishingController controller;
    private final AnimationTimer timer;

    public FishingView(FishingGame game, FishingController controller) {
        this.controller = Objects.requireNonNull(controller);
        this.canvas = new Canvas(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        this.renderer = new FishingRenderer(Objects.requireNonNull(game));
        this.timer = createTimer();
        this.getChildren().add(canvas);
        setupInput();
        draw();
    }

    private AnimationTimer createTimer() {
        return new AnimationTimer() {
            private long lastTime;

            @Override
            public void handle(long now) {
                if (lastTime != 0) {
                    controller.onTick((now - lastTime) / 1_000_000_000.0);
                }
                lastTime = now;
                draw();
            }
        };
    }

    private void setupInput() {
        FishingKeyboardInput input = new FishingKeyboardInput(controller);
        setFocusTraversable(true);
        setOnKeyPressed(input::onKeyPressed);
        setOnKeyReleased(input::onKeyReleased);
        sceneProperty().addListener((_, _, newScene) -> {
            if (newScene != null) {
                requestFocus();
                timer.start();
            }
        });
    }

    private void draw() {
        renderer.draw(canvas.getGraphicsContext2D());
    }
}