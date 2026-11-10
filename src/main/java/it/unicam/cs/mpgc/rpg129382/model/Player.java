package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Map;
import java.util.Objects;

/**
 * The player: the rod they use, the coins they own and the fish they have caught.
 */
public class Player {
    private final Inventory inventory = new Inventory();
    private Rod rod;
    private int money;

    /**
     * Creates a new player.
     *
     * @param startingRod the rod the player starts with.
     */
    public Player(Rod startingRod) {
        this.rod = Objects.requireNonNull(startingRod);
    }

    /**
     * {@return the fish the player has caught; changes to it change the player}
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * {@return the rod the player is using}
     */
    public Rod getRod() {
        return rod;
    }

    /**
     * Replaces the rod the player is using.
     *
     * @param rod the new rod.
     */
    public void equip(Rod rod) {
        this.rod = Objects.requireNonNull(rod);
    }

    /**
     * {@return the coins the player owns}
     */
    public int getMoney() {
        return money;
    }

    /**
     * Gives coins to the player.
     *
     * @param amount the coins to add.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public void addMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        this.money += amount;
    }

    /**
     * {@return true if the player owns at least that many coins}
     *
     * @param amount a price.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canAfford(int amount) {
        return this.money >= amount;
    }

    /**
     * Takes coins from the player.
     *
     * @param amount the coins to pay.
     * @throws IllegalArgumentException if the amount is negative or the player cannot afford it.
     */
    public void spend(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        if (!canAfford(amount)) {
            throw new IllegalArgumentException("Not enough money");
        }
        this.money -= amount;
    }

    /**
     * Puts the player back in a saved state, replacing what they have now.
     *
     * @param rod    the rod to use.
     * @param money  the coins to own.
     * @param fishes the fish to own, with their quantities.
     * @throws IllegalArgumentException if the money is negative or a quantity is not positive.
     */
    public void restore(Rod rod, int money, Map<FishType, Integer> fishes) {
        Objects.requireNonNull(fishes);
        if (money < 0) {
            throw new IllegalArgumentException("Money must be non-negative");
        }
        equip(rod);
        this.money = money;
        inventory.clear();
        fishes.forEach(inventory::add);
    }
}