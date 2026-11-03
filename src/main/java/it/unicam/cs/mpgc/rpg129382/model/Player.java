package it.unicam.cs.mpgc.rpg129382.model;

import java.util.Map;
import java.util.Objects;

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

    public Inventory getInventory() {
        return inventory;
    }

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

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        this.money += amount;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canAfford(int amount) {
        return this.money >= amount;
    }

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
     */
    public void restore(Rod rod, int money, Map<FishType, Integer> fishes) {
        Objects.requireNonNull(fishes);
        if (money < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        equip(rod);
        this.money = money;
        inventory.clear();
        fishes.forEach(inventory::add);
    }
}