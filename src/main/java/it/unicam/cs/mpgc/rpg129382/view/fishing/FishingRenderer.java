package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.config.GameConfig;
import it.unicam.cs.mpgc.rpg129382.logic.Area;
import it.unicam.cs.mpgc.rpg129382.logic.FishingState;
import it.unicam.cs.mpgc.rpg129382.model.Fish;
import it.unicam.cs.mpgc.rpg129382.model.Heading;
import it.unicam.cs.mpgc.rpg129382.view.utils.ImageLoader;
import it.unicam.cs.mpgc.rpg129382.view.utils.SpriteSheet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;

final class FishingRenderer {
    private static final double FISH_DRAW_WIDTH = 120;
    private static final boolean SHOW_CATCH_ZONE = true;

    private final FishingState state;
    private final Image background;
    private final Image hookImage;
    private final SpriteSheet fishSprite;
    private final SpriteSheet hookedFishSprite;
    private final FishingHud hud;

    FishingRenderer(FishingState state) {
        this.state = Objects.requireNonNull(state);
        this.background = ImageLoader.loadImage("/images/lake.png");
        this.hookImage = ImageLoader.loadImage("/images/hook.png");
        this.fishSprite = new SpriteSheet(ImageLoader.loadImage("/images/trout.png"), 2, 2);
        this.hookedFishSprite = new SpriteSheet(ImageLoader.loadImage("/images/hookedTrout.png"), 2, 2);
        this.hud = new FishingHud(state);
    }

    void draw(GraphicsContext gc) {
        gc.drawImage(background, 0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        if (SHOW_CATCH_ZONE) {
            drawCatchZone(gc);
        }
        drawFishes(gc);
        drawHook(gc);
        hud.draw(gc);
    }

    private void drawCatchZone(GraphicsContext gc) {
        Area zone = state.getCatchZone();
        gc.setFill(Color.rgb(255, 255, 255, 0.25));
        gc.fillRect(zone.left(), zone.top(), zone.right() - zone.left(), zone.bottom() - zone.top());
    }

    private void drawFishes(GraphicsContext gc) {
        for (Fish fish : state.getFishes()) {
            SpriteSheet sprite = fish.isHooked() ? hookedFishSprite : fishSprite;
            sprite.drawFrame(gc, 0, fish.getX(), fish.getY(), FISH_DRAW_WIDTH,
                    fish.getHeading() == Heading.LEFT);
        }
    }

    private void drawHook(GraphicsContext gc) {
        if (state.hasHookedFish()) {
            return;
        }
        gc.drawImage(hookImage,
                state.getHook().getX() - hookImage.getWidth() / 2,
                state.getHook().getY() - hookImage.getHeight() / 2);
    }
}