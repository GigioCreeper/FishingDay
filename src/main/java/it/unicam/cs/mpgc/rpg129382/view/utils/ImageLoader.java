package it.unicam.cs.mpgc.rpg129382.view.utils;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;

public final class ImageLoader {

    private ImageLoader() {
    }

    public static Image loadImage(String path) {
        Objects.requireNonNull(path);
        URL resource = ImageLoader.class.getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("Image not found: " + path);
        }
        return new Image(resource.toExternalForm());
    }
}