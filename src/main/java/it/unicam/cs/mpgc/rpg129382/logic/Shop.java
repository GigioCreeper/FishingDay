package it.unicam.cs.mpgc.rpg129382.logic;

import it.unicam.cs.mpgc.rpg129382.model.Inventory;
import it.unicam.cs.mpgc.rpg129382.model.Player;

import java.util.Objects;

public class Shop {
    public int sellAll(Player player){
        Objects.requireNonNull(player);
        Inventory inventory = player.getInventory();
        int total = inventory.getFishes().entrySet().stream()
                .mapToInt(entry -> entry.getKey().value() * entry.getValue())
                .sum();
        inventory.clear();
        player.addMoney(total);
        return total;
    }
}
