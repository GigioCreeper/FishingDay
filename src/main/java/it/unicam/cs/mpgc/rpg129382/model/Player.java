package it.unicam.cs.mpgc.rpg129382.model;

public class Player {
    private final Inventory inventory = new Inventory();
    private int money;

    public Inventory getInventory() {
        return inventory;
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
}
