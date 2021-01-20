package de.cb.poxh.manager.shop;

import org.bukkit.inventory.ItemStack;

public class PlayerShopManager {

    private final int itemPrice;
    private final ItemStack itemStack;

    public PlayerShopManager(int itemPrice, ItemStack itemStack) {
        this.itemPrice = itemPrice;
        this.itemStack = itemStack;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
