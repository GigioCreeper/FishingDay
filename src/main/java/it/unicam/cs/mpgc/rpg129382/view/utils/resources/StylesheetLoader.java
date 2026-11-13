package it.unicam.cs.mpgc.rpg129382.view.utils.resources;

import java.net.URL;
import java.util.Objects;

/**
 * Loads the stylesheets bundled with the game.
 */
public final class StylesheetLoader {

    private StylesheetLoader() {
    }

    /**
     * {@return the address of the stylesheet, ready to be added to a node's stylesheets}
     *
     * @param path the path of the stylesheet on the classpath, e.g. "/css/Common.css".
     * @throws IllegalArgumentException if the stylesheet is not found.
     */
    public static String loadStylesheet(String path) {
        Objects.requireNonNull(path);
        URL resource = StylesheetLoader.class.getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("Stylesheet not found: " + path);
        }
        return resource.toExternalForm();
    }
}
