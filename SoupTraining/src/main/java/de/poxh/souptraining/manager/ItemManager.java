package de.poxh.souptraining.manager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemManager {

    private ItemStack itemStack;

    private ItemMeta itemMeta;

    public ItemManager(Material material, short subID) {
        this.itemStack = new ItemStack(material, 1, subID);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemManager(Material material) {
        this.itemStack = new ItemStack(material, 1, (short)0);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemManager setDisplayName(String displayName) {
        this.itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemManager setSubID(byte subID) {
        this.itemStack.getData().setData(subID);
        return this;
    }

    public ItemManager setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemManager setType(Material material) {

        this.itemStack.setType(material);
        return this;
    }

    public ItemManager setAmount(Integer amount) {
        this.itemStack.setAmount(amount.intValue());
        return this;
    }

    public ItemManager addEnchantment(Enchantment enchantment) {
        this.itemMeta.addEnchant(enchantment, 1, false);
        return this;
    }

    public ItemManager addEnchantment(Enchantment enchantment, Integer power) {
        this.itemMeta.addEnchant(enchantment, power.intValue(), false);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}