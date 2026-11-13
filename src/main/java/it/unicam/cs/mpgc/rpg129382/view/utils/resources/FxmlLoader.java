package it.unicam.cs.mpgc.rpg129382.view.utils.resources;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Loads the FXML screens bundled with the game.
 */
public final class FxmlLoader {

    private FxmlLoader() {
    }

    /**
     * Loads a screen from an FXML file, giving it the controller it needs.
     *
     * @param path       the path of the FXML file on the classpath, e.g. "/fxml/Shop.fxml".
     * @param controller creates the controller of the screen.
     * @return the root node of the loaded screen.
     * @throws IllegalArgumentException if the FXML file is not found.
     * @throws UncheckedIOException     if the FXML file cannot be read or is not valid.
     */
    public static Parent loadFxml(String path, Supplier<?> controller) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(controller);
        URL resource = FxmlLoader.class.getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("FXML not found: " + path);
        }
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(_ -> controller.get());
        try {
            return loader.load();
        } catch (IOException e) {
            throw new UncheckedIOException("Could not load " + path, e);
        }
    }
}