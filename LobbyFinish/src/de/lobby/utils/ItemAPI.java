package de.lobby.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemAPI {

    private ItemStack item;
    private ItemMeta meta;

    public ItemAPI(Material material) {
        item = new ItemStack(material, 1);
        meta = item.getItemMeta();
    }

    public ItemAPI(Material material, short subID) {
        item = new ItemStack(material, 1, subID);
        meta = item.getItemMeta();
    }

    public ItemAPI setName(String displayname) {
        meta.setDisplayName(displayname);
        return this;
    }

    public ItemAPI setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
