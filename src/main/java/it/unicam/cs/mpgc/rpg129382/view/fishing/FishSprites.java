package it.unicam.cs.mpgc.rpg129382.view.fishing;

import it.unicam.cs.mpgc.rpg129382.model.ReadOnlyFish;
import it.unicam.cs.mpgc.rpg129382.view.utils.ImageLoader;
import it.unicam.cs.mpgc.rpg129382.view.utils.SpriteSheet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Chooses the sprite of each fish by its type name, falling back to a default one.
 * Giving a new fish type its own look only requires registering its sprites.
 */
final class FishSprites {
    private final Sprites fallback;
    private final Map<String, Sprites> byTypeName = new HashMap<>();

    FishSprites() {
        this.fallback = load("trout");
    }

    void register(String typeName, String imageName) {
        byTypeName.put(Objects.requireNonNull(typeName), load(imageName));
    }

    SpriteSheet spriteFor(ReadOnlyFish fish) {
        Sprites sprites = byTypeName.getOrDefault(fish.getType().name(), fallback);
        return fish.isHooked() ? sprites.hooked() : sprites.swimming();
    }

    private static Sprites load(String imageName) {
        String hookedName = "hooked" + Character.toUpperCase(imageName.charAt(0)) + imageName.substring(1);
        return new Sprites(sheet(imageName), sheet(hookedName));
    }

    private static SpriteSheet sheet(String imageName) {
        return new SpriteSheet(ImageLoader.loadImage("/images/" + imageName + ".png"), 2, 2);
    }

    private record Sprites(SpriteSheet swimming, SpriteSheet hooked) {
    }
}
