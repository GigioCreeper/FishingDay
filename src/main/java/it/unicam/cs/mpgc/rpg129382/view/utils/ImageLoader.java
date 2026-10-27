package it.unicam.cs.mpgc.rpg129382.view.utils;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;

public final class ImageLoader {

    private ImageLoader() {
    }

    public static Image loadImage(String path) {
        return findImage(path).orElseThrow(() -> new IllegalArgumentException("Image not found: " + path));
    }

    public static Optional<Image> findImage(String path) {
        Objects.requireNonNull(path);
        URL resource = ImageLoader.class.getResource(path);
        if (resource == null) {
            return Optional.empty();
        }
        return Optional.of(new Image(resource.toExternalForm()));
    }
}