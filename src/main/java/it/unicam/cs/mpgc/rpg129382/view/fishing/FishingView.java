package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.controller.FishingController;
import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.Objects;

import static it.unicam.cs.mpgc.rpg129382.view.utils.ImageLoader.loadImage;

public class FishingView extends Pane {
    private final Canvas canvas;
    private final Image background;
    private final Image hookImage;
    private final FishingGame game;
    private final FishingController controller;
    private final AnimationTimer timer;

    public FishingView(FishingGame game, FishingController controller) {
        this.game = Objects.requireNonNull(game);
        this.controller = Objects.requireNonNull(controller);
        this.canvas = new Canvas(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        this.background = loadImage("/images/lake.png");
        this.hookImage = loadImage("/images/hook.png");
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
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(hookImage,
                game.getHook().getX() - hookImage.getWidth() / 2,
                game.getHook().getY() - hookImage.getHeight() / 2);
    }
}