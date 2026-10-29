package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.FishCatalog;
import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.logic.fishing.FishingState;
import it.unicam.cs.mpgc.rpg129382.model.Area;
import it.unicam.cs.mpgc.rpg129382.model.Heading;
import it.unicam.cs.mpgc.rpg129382.model.Positioned;
import it.unicam.cs.mpgc.rpg129382.model.ReadOnlyFish;
import it.unicam.cs.mpgc.rpg129382.view.utils.ImageLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

final class FishingRenderer {
    private static final double FISH_DRAW_WIDTH = 120;
    private static final double BOTTLE_DRAW_WIDTH = 60;
    private static final int PIXELS_PER_FRAME = 25;

    private final FishingState state;
    private final boolean showCatchZone;
    private final Image background;
    private final Image hookImage;
    private final Image bottleImage;
    private final FishSprites fishSprites;
    private final FishingHud hud;

    FishingRenderer(FishingState state, boolean showCatchZone) {
        this.state = Objects.requireNonNull(state);
        this.showCatchZone = showCatchZone;
        this.background = ImageLoader.loadImage("/images/lake.png");
        this.hookImage = ImageLoader.loadImage("/images/hook.png");
        this.bottleImage = ImageLoader.loadImage("/images/story/bottle.png");
        this.fishSprites = new FishSprites();
        fishSprites.register(FishCatalog.PERCH, "perch");
        fishSprites.register(FishCatalog.TROUT, "trout");
        fishSprites.register(FishCatalog.PIKE, "pike");
        fishSprites.register(FishCatalog.BASS, "bass");
        fishSprites.register(FishCatalog.CHUB, "chub");
        this.hud = new FishingHud(state);
    }

    void draw(GraphicsContext gc) {
        gc.drawImage(background, 0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        if (showCatchZone) {
            drawCatchZone(gc);
        }
        drawFishes(gc);
        state.getBottle().ifPresent(bottle -> drawBottle(gc, bottle));
        drawHook(gc);
        hud.draw(gc);
    }

    private void drawCatchZone(GraphicsContext gc) {
        Area zone = state.getCatchZone();
        gc.setFill(Color.rgb(255, 255, 255, 0.25));
        gc.fillRect(zone.left(), zone.top(), zone.right() - zone.left(), zone.bottom() - zone.top());
    }

    private void drawFishes(GraphicsContext gc) {
        for (ReadOnlyFish fish : state.getFishes()) {
            fishSprites.spriteFor(fish).drawFrame(gc, frameFor(fish), fish.getX(), fish.getY(), FISH_DRAW_WIDTH,
                    fish.getHeading() == Heading.LEFT);
        }
    }

    private void drawBottle(GraphicsContext gc, Positioned bottle) {
        double height = BOTTLE_DRAW_WIDTH * bottleImage.getHeight() / bottleImage.getWidth();
        gc.drawImage(bottleImage,
                bottle.getX() - BOTTLE_DRAW_WIDTH / 2,
                bottle.getY() - height / 2,
                BOTTLE_DRAW_WIDTH, height);
    }

    private void drawHook(GraphicsContext gc) {
        if (state.hasHookedFish()) {
            return;
        }
        gc.drawImage(hookImage,
                state.getHook().getX() - hookImage.getWidth() / 2,
                state.getHook().getY() - hookImage.getHeight() / 2);
    }

    private int frameFor(ReadOnlyFish fish) {
        return (int) (fish.getX() / PIXELS_PER_FRAME);
    }
}