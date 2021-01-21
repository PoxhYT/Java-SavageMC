package de.poxh.lobby.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemBuilderAPI {

    private ItemStack itemStack;

    private ItemMeta itemMeta;

    public ItemBuilderAPI(Material material, short subID) {
        this.itemStack = new ItemStack(material, 1, subID);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilderAPI(Material material) {
        this.itemStack = new ItemStack(material, 1, (short)0);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilderAPI setDisplayName(String displayName) {
        this.itemMeta.setDisplayName(displayName);
        return this;
    }

    public static ItemStack getHead(Player player, String displayName, String itemLore) {
        int lifePlayer = (int) player.getHealth();
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(displayName);
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(itemLore);
        skull.setLore(lore);
        skull.setOwner(player.getName());
        item.setItemMeta(skull);
        return item;
    }

    public ItemBuilderAPI setSubID(byte subID) {
        this.itemStack.getData().setData(subID);
        return this;
    }

    public ItemBuilderAPI setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilderAPI setType(Material material) {

        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilderAPI setAmount(Integer amount) {
        this.itemStack.setAmount(amount.intValue());
        return this;
    }

    public ItemBuilderAPI addEnchantment(Enchantment enchantment) {
        this.itemMeta.addEnchant(enchantment, 1, false);
        return this;
    }

    public ItemBuilderAPI addEnchantment(Enchantment enchantment, Integer power) {
        this.itemMeta.addEnchant(enchantment, power.intValue(), false);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}
