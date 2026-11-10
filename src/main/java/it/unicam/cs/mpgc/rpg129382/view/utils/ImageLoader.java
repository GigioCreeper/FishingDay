package it.unicam.cs.mpgc.rpg129382.view.utils;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;

/**
 * Loads the images bundled with the game.
 */
public final class ImageLoader {

    private ImageLoader() {
    }

    /**
     * Loads an image from a given path.
     *
     * @param path the path of the image on the classpath, e.g. "/images/lake.png".
     * @return the loaded image.
     * @throws IllegalArgumentException if the image is not found.
     */
    public static Image loadImage(String path) {
        return findImage(path).orElseThrow(() -> new IllegalArgumentException("Image not found: " + path));
    }

    /**
     * Loads an image from a given path that may be missing.
     *
     * @param path the path of the image on the classpath.
     * @return the loaded image, or empty if it is not found.
     */
    public static Optional<Image> findImage(String path) {
        Objects.requireNonNull(path);
        URL resource = ImageLoader.class.getResource(path);
        if (resource == null) {
            return Optional.empty();
        }
        return Optional.of(new Image(resource.toExternalForm()));
    }
}