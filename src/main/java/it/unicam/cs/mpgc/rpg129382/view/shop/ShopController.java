package it.unicam.cs.mpgc.rpg129382.view.shop;

import it.unicam.cs.mpgc.rpg129382.logic.shop.PurchaseResult;
import it.unicam.cs.mpgc.rpg129382.logic.shop.Shop;
import it.unicam.cs.mpgc.rpg129382.model.Inventory;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.model.Rod;
import it.unicam.cs.mpgc.rpg129382.view.navigation.GameScreen;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenNavigator;
import it.unicam.cs.mpgc.rpg129382.view.utils.ImageLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Connects the shop screen to the shop rules and keeps it up to date.
 */
public final class ShopController {
    private static final String COIN_IMAGE = "/images/shop/coin.png";

    private final Shop shop;
    private final Player player;
    private final ScreenNavigator navigator;
    private final Map<Rod, RodCard> rodCards = new LinkedHashMap<>();

    @FXML
    private ImageView moneyIcon;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label catchLabel;
    @FXML
    private HBox rodCardsBox;
    @FXML
    private Label messageLabel;
    @FXML
    private Button sellButton;

    /**
     * Creates a new ShopController.
     * @param shop the shop rules.
     * @param player the player who uses the shop.
     * @param navigator the navigator used to leave the shop.
     */
    public ShopController(Shop shop, Player player, ScreenNavigator navigator) {
        this.shop = Objects.requireNonNull(shop);
        this.player = Objects.requireNonNull(player);
        this.navigator = Objects.requireNonNull(navigator);
    }

    @FXML
    private void initialize() {
        Image coin = ImageLoader.loadImage(COIN_IMAGE);
        moneyIcon.setImage(coin);
        for (Rod rod : shop.rods()) {
            RodCard card = new RodCard(rod, coin, this::buyNextRod);
            rodCards.put(rod, card);
            rodCardsBox.getChildren().add(card);
        }
        refresh();
    }

    @FXML
    private void onSellClicked() {
        int earned = shop.sellAll(player);
        messageLabel.setText("You earned " + earned + " coins");
        refresh();
    }

    @FXML
    private void onBackClicked() {
        navigator.navigateTo(GameScreen.FISHING);
    }

    private void buyNextRod() {
        messageLabel.setText(describe(shop.buyNextRod(player)));
        refresh();
    }

    private void refresh() {
        Inventory inventory = player.getInventory();
        moneyLabel.setText(String.valueOf(player.getMoney()));
        catchLabel.setText("Caught: " + inventory.totalCount() + " fish (" + inventory.totalValue() + " coins)");
        sellButton.setDisable(inventory.totalCount() == 0);
        rodCards.forEach((rod, card) -> card.show(shop.statusOf(rod, player)));
    }

    private String describe(PurchaseResult result) {
        return switch (result) {
            case PURCHASED -> "New rod: " + player.getRod().name();
            case NOT_ENOUGH_MONEY -> "Not enough coins";
            case MAX_LEVEL_REACHED -> "You already have the best rod";
        };
    }
}