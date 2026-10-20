package it.unicam.cs.mpgc.rpg129382.view.utils;

import it.unicam.cs.mpgc.rpg129382.view.fishing.FishingView;
import javafx.scene.image.Image;

import java.util.Objects;


/**
 * Utility class for loading images from resources.
 */
public final class ImageLoader {
    public static Image loadImage(String path) {
        return new Image(Objects.requireNonNull(
                FishingView.class.getResourceAsStream(path),
                "Immagine non trovata: " + path));
    }
}
