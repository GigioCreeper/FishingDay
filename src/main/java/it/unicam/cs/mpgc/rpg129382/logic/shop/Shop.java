package it.unicam.cs.mpgc.rpg129382.logic.shop;

import it.unicam.cs.mpgc.rpg129382.config.RodCatalog;
import it.unicam.cs.mpgc.rpg129382.model.Inventory;
import it.unicam.cs.mpgc.rpg129382.model.Player;
import it.unicam.cs.mpgc.rpg129382.model.Rod;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The shop where the player sells fishes and upgrades the equipment.
 */
public final class Shop {

    /**
     * Sells every fish in the player's inventory.
     *
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
     * @return every rod sold by the shop, from the starting one to the best one.
     */
    public List<Rod> rods() {
        return RodCatalog.all();
    }

    /**
     * @param rod    a rod of the catalog.
     * @param player the player who looks at it.
     * @return how the rod relates to what the player owns.
     * @throws IllegalArgumentException if the rod is not in the catalog.
     */
    public RodStatus statusOf(Rod rod, Player player) {
        Objects.requireNonNull(rod);
        Objects.requireNonNull(player);
        int rodLevel = RodCatalog.levelOf(rod);
        int currentLevel = RodCatalog.levelOf(player.getRod());
        if (rodLevel == currentLevel) {
            return RodStatus.EQUIPPED;
        }
        if (rodLevel < currentLevel) {
            return RodStatus.OWNED;
        }
        if (rodLevel == currentLevel + 1) {
            return RodStatus.AVAILABLE;
        }
        return RodStatus.LOCKED;
    }

    /**
     * Buys the next rod and equips it, if there is one and the player can afford it.
     *
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

    /**
     * @param player the player who wants to upgrade.
     * @return the rod the player can buy next, or empty if the current one is the best.
     */
    private Optional<Rod> nextRod(Player player) {
        Objects.requireNonNull(player);
        return RodCatalog.nextAfter(player.getRod());
    }
}