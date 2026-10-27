package it.unicam.cs.mpgc.rpg129382.logic.shop;

import it.unicam.cs.mpgc.rpg129382.config.RodCatalog;
import it.unicam.cs.mpgc.rpg129382.model.Inventory;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.model.Rod;

import java.util.Objects;
import java.util.Optional;

/**
 * The shop where the player sells fishes and upgrades the equipment.
 */
public final class Shop {

    /**
     * Sells every fish in the player's inventory.
     * @param player the player who sells.
     * @return the money earned.
     */
    public int sellAll(Player player) {
        Objects.requireNonNull(player);
        Inventory inventory = player.getInventory();
        int total = inventory.totalValue();
        inventory.clear();
        player.addMoney(total);
        return total;
    }

    /**
     * @param player the player who wants to upgrade.
     * @return the rod the player can buy next, or empty if the current one is the best.
     */
    public Optional<Rod> nextRod(Player player) {
        Objects.requireNonNull(player);
        return RodCatalog.nextAfter(player.getRod());
    }

    /**
     * Buys the next rod level and equips it, if the player can afford it.
     * @param player the player who buys.
     * @return the result of the purchase.
     */
    public PurchaseResult buyNextRod(Player player) {
        Optional<Rod> next = nextRod(player);
        if (next.isEmpty()) {
            return PurchaseResult.MAX_LEVEL_REACHED;
        }
        Rod rod = next.get();
        if (!player.canAfford(rod.price())) {
            return PurchaseResult.NOT_ENOUGH_MONEY;
        }
        player.spend(rod.price());
        player.equip(rod);
        return PurchaseResult.PURCHASED;
    }
}