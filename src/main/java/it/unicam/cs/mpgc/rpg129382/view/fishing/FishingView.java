package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.logic.Updatable;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingCommands;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingState;
import it.unicam.cs.mpgc.rpg129382.logic.story.StoryMessage;
import it.unicam.cs.mpgc.rpg129382.view.utils.graphics.SharpCanvas;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.util.Objects;

final class FishingView extends Pane {
    private final FishingState state;
    private final SharpCanvas canvas;
    private final FishingRenderer renderer;
    private final StoryPanel storyPanel;
    private final Updatable game;
    private final AnimationTimer timer;

    FishingView(FishingState state, FishingCommands commands, Updatable game, Runnable onStoryEnd) {
        this.state = Objects.requireNonNull(state);
        this.game = Objects.requireNonNull(game);
        Objects.requireNonNull(onStoryEnd);
        Objects.requireNonNull(commands);
        this.canvas = new SharpCanvas(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        this.renderer = new FishingRenderer(state, GameConfig.SHOW_CATCH_ZONE);
        this.storyPanel = new StoryPanel(() -> {
            boolean isEnding = state.getStoryMessage().map(StoryMessage::isEnding).orElse(false);
            commands.closeStoryMessage();
            if (isEnding) {
                onStoryEnd.run();
            } else {
                requestFocus();
            }
        });
        this.timer = createTimer();
        this.getChildren().addAll(canvas, storyPanel);
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
        renderer.draw(canvas.beginFrame());
        state.getStoryMessage().ifPresentOrElse(storyPanel::show, storyPanel::hide);
    }
}