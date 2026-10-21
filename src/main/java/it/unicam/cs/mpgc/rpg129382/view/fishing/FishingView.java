package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.controller.FishingController;
import it.unicam.cs.mpgc.rpg129382.logic.FishingGame;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Heading;
import it.unicam.cs.mpgc.rpg129382.view.utils.ImageLoader;
import it.unicam.cs.mpgc.rpg129382.view.utils.SpriteSheet;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public class FishingView extends Pane {
    private static final double FISH_DRAW_WIDTH = 120;
    private static final Font HUD_FONT = Font.font("Arial", FontWeight.BOLD, 32);
    private static final double HUD_X = 30;
    private static final double HUD_Y = 50;

    private final Canvas canvas;
    private final Image background;
    private final Image hookImage;
    private final SpriteSheet fishSprite;
    private final SpriteSheet hookedFishSprite;
    private final FishingGame game;
    private final FishingController controller;
    private final AnimationTimer timer;

    public FishingView(FishingGame game, FishingController controller) {
        this.game = Objects.requireNonNull(game);
        this.controller = Objects.requireNonNull(controller);
        this.canvas = new Canvas(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        this.background = ImageLoader.loadImage("/images/lake.png");
        this.hookImage = ImageLoader.loadImage("/images/hook.png");
        this.fishSprite = new SpriteSheet(ImageLoader.loadImage("/images/trout.png"), 2, 2);
        this.hookedFishSprite = new SpriteSheet(ImageLoader.loadImage("/images/hookedTrout.png"), 2, 2);
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
        drawFishes(gc);
        drawHook(gc);
        drawCounter(gc);
    }

    private void drawFishes(GraphicsContext gc) {
        for (Fish fish : game.getFishes()) {
            SpriteSheet sprite = fish.isHooked() ? hookedFishSprite : fishSprite;
            sprite.drawFrame(gc, 0, fish.getX(), fish.getY(), FISH_DRAW_WIDTH,
                    fish.getHeading() == Heading.LEFT);
        }
    }

    private void drawHook(GraphicsContext gc) {
        if (game.hasHookedFish()) {
            return;
        }
        gc.drawImage(hookImage,
                game.getHook().getX() - hookImage.getWidth() / 2,
                game.getHook().getY() - hookImage.getHeight() / 2);
    }

    private void drawCounter(GraphicsContext gc) {
        String text = "Pesci pescati: " + game.getCaughtFishes().size();
        gc.setFont(HUD_FONT);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeText(text, HUD_X, HUD_Y);
        gc.setFill(Color.WHITE);
        gc.fillText(text, HUD_X, HUD_Y);
    }
}