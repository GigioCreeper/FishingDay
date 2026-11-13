package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.model.FishType;
import it.unicam.cs.mpgc.rpg129382.model.ReadOnlyFish;
import it.unicam.cs.mpgc.rpg129382.view.utils.resources.ImageLoader;
import it.unicam.cs.mpgc.rpg129382.view.utils.graphics.SpriteSheet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Loads and stores the sprites used to draw fishes.
 */
final class FishSprites {
    private static final int COLUMNS = 2;
    private static final int ROWS = 2;
    private final double defaultWidth;
    private final Map<FishType, Sprites> byType = new HashMap<>();

    /**
     * Creates an empty set of sprites.
     *
     * @param defaultWidth how wide fishes are drawn, unless registered with their own width.
     */
    FishSprites(double defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    void register(FishType type) {
        register(type, defaultWidth);
    }

    void register(FishType type, double width) {
        byType.put(Objects.requireNonNull(type), load(type.name(), width));
    }

    double widthFor(ReadOnlyFish fish) {
        return spritesFor(fish).width();
    }

    SpriteSheet spriteFor(ReadOnlyFish fish) {
        Sprites sprites = spritesFor(fish);
        return fish.isHooked() ? sprites.hooked() : sprites.swimming();
    }

    private Sprites spritesFor(ReadOnlyFish fish) {
        Sprites sprites = byType.get(fish.getType());
        if (sprites == null) {
            throw new IllegalStateException("No sprites registered for " + fish.getType().name());
        }
        return sprites;
    }

    private static Sprites load(String imageName, double width) {
        String hookedName = "hooked" + Character.toUpperCase(imageName.charAt(0)) + imageName.substring(1);
        SpriteSheet swimming = sheet(imageName);
        SpriteSheet hooked = ImageLoader.findImage("/images/" + hookedName + ".png")
                .map(img -> new SpriteSheet(img, COLUMNS, ROWS))
                .orElse(swimming);
        return new Sprites(swimming, hooked, width);
    }

    private static SpriteSheet sheet(String imageName) {
        return new SpriteSheet(ImageLoader.loadImage("/images/" + imageName + ".png"), COLUMNS, ROWS);
    }

    private record Sprites(SpriteSheet swimming, SpriteSheet hooked, double width) {
    }
}
