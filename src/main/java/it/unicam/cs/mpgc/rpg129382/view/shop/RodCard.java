package it.unicam.cs.mpgc.rpg129382.view.shop;

import it.unicam.cs.mpgc.rpg129382.logic.shop.RodStatus;
import it.unicam.cs.mpgc.rpg129382.model.Rod;
import it.unicam.cs.mpgc.rpg129382.view.utils.ImageLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * A card of the shop showing one rod, its price and whether it can be bought.
 * Its look for each {@link RodStatus} is defined in Shop.css through a style class
 * named after the status (for example {@code .rod-card.locked}).
 */
final class RodCard extends VBox {
    private static final double ROD_IMAGE_HEIGHT = 120;
    private static final double COIN_SIZE = 24;
    private static final List<String> STATUS_CLASSES = Arrays.stream(RodStatus.values())
            .map(RodCard::styleClassOf)
            .toList();

    private final HBox priceBox;
    private final Label statusLabel = new Label();
    private final Button buyButton = new Button("Buy");

    /**
     * Creates the card of a rod; its status is shown later, with {@link #show(RodStatus)}.
     *
     * @param rod   the rod shown by the card.
     * @param coin  the coin icon shown next to the price.
     * @param onBuy what to do when the player buys the rod.
     */
    RodCard(Rod rod, Image coin, Runnable onBuy) {
        Objects.requireNonNull(rod);
        Objects.requireNonNull(onBuy);
        getStyleClass().add("rod-card");

        ImageView rodImage = new ImageView(ImageLoader.loadImage(
                "/images/shop/" + rod.name().toLowerCase(Locale.ROOT) + ".png"));
        rodImage.setFitHeight(ROD_IMAGE_HEIGHT);
        rodImage.setPreserveRatio(true);

        Label name = new Label(rod.name());
        name.getStyleClass().add("rod-name");
        Label power = new Label("Power " + Math.round(rod.power()));
        power.getStyleClass().add("rod-detail");

        ImageView coinIcon = new ImageView(Objects.requireNonNull(coin));
        coinIcon.setFitHeight(COIN_SIZE);
        coinIcon.setPreserveRatio(true);
        Label price = new Label(String.valueOf(rod.price()));
        price.getStyleClass().add("rod-detail");
        priceBox = new HBox(6, coinIcon, price);
        priceBox.setAlignment(Pos.CENTER);

        statusLabel.getStyleClass().add("rod-detail");
        StackPane priceOrStatus = new StackPane(priceBox, statusLabel);

        buyButton.getStyleClass().add("main-button");
        buyButton.setMnemonicParsing(false);
        buyButton.setOnAction(_ -> onBuy.run());

        getChildren().addAll(rodImage, name, power, priceOrStatus, buyButton);
    }

    /**
     * Updates the card to show the given status.
     *
     * @param status how the rod relates to the player.
     */
    void show(RodStatus status) {
        getStyleClass().removeAll(STATUS_CLASSES);
        getStyleClass().add(styleClassOf(status));
        boolean forSale = status == RodStatus.AVAILABLE || status == RodStatus.LOCKED;
        priceBox.setVisible(forSale);
        statusLabel.setVisible(!forSale);
        statusLabel.setText(status == RodStatus.EQUIPPED ? "In use" : "Owned");
        buyButton.setVisible(status == RodStatus.AVAILABLE);
    }

    private static String styleClassOf(RodStatus status) {
        return status.name().toLowerCase(Locale.ROOT);
    }
}