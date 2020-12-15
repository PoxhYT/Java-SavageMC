package de.lobby.listener;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CrateInventory {

    private Inventory inv;
    private Plugin plugin;
    private ItemStack[] contents;

    private InventoryClickHandler helper;

    private int itemIndex = 0;

    public CrateInventory(int size, ItemStack[] contents, String name,
                          Plugin main) {
        inv = Bukkit.createInventory(null, size, name);
        this.plugin = main;
        this.contents = contents;
        helper = new InventoryClickHandler(main, name);
        shuffle();
    }
}

