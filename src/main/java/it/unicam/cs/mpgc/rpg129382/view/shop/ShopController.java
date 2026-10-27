package it.unicam.cs.mpgc.rpg129382.view.shop;

import it.unicam.cs.mpgc.rpg129382.logic.shop.PurchaseResult;
import it.unicam.cs.mpgc.rpg129382.logic.shop.Shop;
import it.unicam.cs.mpgc.rpg129382.model.Inventory;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.model.Rod;
import it.unicam.cs.mpgc.rpg129382.view.navigation.GameScreen;
import it.unicam.cs.mpgc.rpg129382.view.navigation.ScreenNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Objects;
import java.util.Optional;

/**
 * Connects the shop screen to the shop rules and keeps its labels up to date.
 */
public final class ShopController {
    private final Shop shop;
    private final Player player;
    private final ScreenNavigator navigator;

    @FXML
    private Label moneyLabel;
    @FXML
    private Label catchLabel;
    @FXML
    private Button sellButton;
    @FXML
    private Label rodLabel;
    @FXML
    private Button upgradeButton;
    @FXML
    private Label messageLabel;

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
        refresh();
    }

    @FXML
    private void onSellClicked() {
        int earned = shop.sellAll(player);
        messageLabel.setText("Hai guadagnato " + earned + " monete");
        refresh();
    }

    @FXML
    private void onUpgradeClicked() {
        messageLabel.setText(describe(shop.buyNextRod(player)));
        refresh();
    }

    @FXML
    private void onBackClicked() {
        navigator.navigateTo(GameScreen.FISHING);
    }

    private void refresh() {
        Inventory inventory = player.getInventory();
        moneyLabel.setText("Monete: " + player.getMoney());
        catchLabel.setText("Pescato: " + inventory.totalCount() + " pesci (" + inventory.totalValue() + " monete)");
        sellButton.setDisable(inventory.totalCount() == 0);

        String currentRod = "Canna: " + player.getRod().name();
        Optional<Rod> next = shop.nextRod(player);
        rodLabel.setText(next.map(rod -> currentRod + " - prossima: " + rod.name())
                .orElse(currentRod + " (livello massimo)"));
        upgradeButton.setText(next.map(rod -> "Compra (" + rod.price() + ")").orElse("Al massimo"));
        upgradeButton.setDisable(next.isEmpty());
    }

    private String describe(PurchaseResult result) {
        return switch (result) {
            case PURCHASED -> "Nuova canna: " + player.getRod().name();
            case NOT_ENOUGH_MONEY -> "Non hai abbastanza monete";
            case MAX_LEVEL_REACHED -> "Hai già la canna migliore";
        };
    }
}