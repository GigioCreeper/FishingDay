package it.unicam.cs.mpgc.rpg129382.view;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;
import it.unicam.cs.mpgc.rpg129382.model.Hook;
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
    private final Hook hook;
    private final FishingGame game;
    private final AnimationTimer timer;

    public FishingView(Hook hook, FishingGame game) {
        this.hook = Objects.requireNonNull(hook);
        this.game = Objects.requireNonNull(game);
        this.canvas = new Canvas(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        this.background = loadImage("/images/lake.png");
        this.hookImage = loadImage("/images/hook.png");
        this.timer = createTimer();
        this.getChildren().add(canvas);
        setupInput();
        draw();
    }
    //Maybe to put in a separate class, but for now it's fine here

    private AnimationTimer createTimer() {
        return new AnimationTimer() {
            private long lastTime;
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                game.update(deltaTime);
                draw();
            }
        };
    }
    private void setupInput() {
        FishingController controller = new FishingController(game);
        setFocusTraversable(true);
        setOnKeyPressed(controller::onKeyPressed);
        setOnKeyReleased(controller::onKeyReleased);
        sceneProperty().addListener((observable, oldScene, newScene) -> {
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
                hook.getX() - hookImage.getWidth() / 2,
                hook.getY() - hookImage.getHeight() / 2);
    }
}