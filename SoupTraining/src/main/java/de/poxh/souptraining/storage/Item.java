package de.poxh.souptraining.storage;

import org.bukkit.inventory.ItemStack;

public class Item {

    private static ItemStack item;

    public static void setItem(ItemStack itemStack) {
        item = itemStack;
    }

    public static ItemStack getItem() {
        return item;
    }
}
