package it.unicam.cs.mpgc.rpg129382.view.shop;

import it.unicam.cs.mpgc.rpg129382.logic.shop.Shop;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenFactory;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenNavigator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * Creates the shop screen from its FXML file.
 */
public final class ShopScreenFactory implements ScreenFactory {
    private final Shop shop;
    private final Player player;
    private final ScreenNavigator navigator;

    /**
     * Creates a new ShopScreenFactory.
     *
     * @param shop      the shop rules.
     * @param player    the player who uses the shop.
     * @param navigator the navigator used to leave the shop.
     */
    public ShopScreenFactory(Shop shop, Player player, ScreenNavigator navigator) {
        this.shop = Objects.requireNonNull(shop);
        this.player = Objects.requireNonNull(player);
        this.navigator = Objects.requireNonNull(navigator);
    }

    /**
     * Creates and returns the content of the shop screen.
     *
     * @return the Parent node representing the shop screen.
     * @throws UncheckedIOException if there is an error loading the FXML file.
     */
    @Override
    public Parent createScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Shop.fxml"));
            loader.setControllerFactory(_ -> new ShopController(shop, player, navigator));
            return loader.load();
        } catch (IOException e) {
            throw new UncheckedIOException("Could not load Shop.fxml", e);
        }
    }
}