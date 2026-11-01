package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.model.FishType;
import it.unicam.cs.mpgc.rpg129382.model.ReadOnlyFish;
import it.unicam.cs.mpgc.rpg129382.view.utils.ImageLoader;
import it.unicam.cs.mpgc.rpg129382.view.utils.SpriteSheet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Chooses the sprite of each fish by its type, falling back to a default one.
 * Giving a new fish type its own look only requires registering its sprites.
 */
final class FishSprites {
    private final double defaultWidth;
    private final Sprites fallback;
    private final Map<FishType, Sprites> byTypeName = new HashMap<>();

    /**
     * @param defaultWidth how wide fishes are drawn, unless registered with their own width.
     */
    FishSprites(double defaultWidth) {
        this.defaultWidth = defaultWidth;
        this.fallback = load("trout", defaultWidth);
    }

    void register(FishType typeName, String imageName) {
        register(typeName, imageName, defaultWidth);
    }

    void register(FishType typeName, String imageName, double width) {
        byTypeName.put(Objects.requireNonNull(typeName), load(imageName, width));
    }

    double widthFor(ReadOnlyFish fish) {
        return byTypeName.getOrDefault(fish.getType(), fallback).width();
    }

    SpriteSheet spriteFor(ReadOnlyFish fish) {
        Sprites sprites = byTypeName.getOrDefault(fish.getType(), fallback);
        return fish.isHooked() ? sprites.hooked() : sprites.swimming();
    }

    private static Sprites load(String imageName, double width) {
        String hookedName = "hooked" + Character.toUpperCase(imageName.charAt(0)) + imageName.substring(1);
        SpriteSheet swimming = sheet(imageName);
        SpriteSheet hooked = ImageLoader.findImage("/images/" + hookedName + ".png")
                .map(img -> new SpriteSheet(img, 2, 2))
                .orElse(swimming);
        return new Sprites(swimming, hooked, width);
    }

    private static SpriteSheet sheet(String imageName) {
        return new SpriteSheet(ImageLoader.loadImage("/images/" + imageName + ".png"), 2, 2);
    }

    private record Sprites(SpriteSheet swimming, SpriteSheet hooked, double width) {
    }
}
