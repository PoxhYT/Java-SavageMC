package de.mlgrush.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;

    private ItemMeta itemMeta;

    public ItemBuilder(Material material, short subID) {
        this.itemStack = new ItemStack(material, 1, subID);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1, (short)0);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder setSubID(byte subID) {
        this.itemStack.getData().setData(subID);
        return this;
    }

    public ItemBuilder setLore(ArrayList<String> lore) {
        this.itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        if (this.itemMeta.getLore() != null) {
            this.itemMeta.getLore().add(line);
        } else {
            List<String> lore = new ArrayList<>();
            lore.add(line);
            this.itemMeta.setLore(lore);
        }
        return this;
    }

    public ItemBuilder addLoreArray(String[] array) {
        if (this.itemMeta.getLore() != null) {
            for (String current : array)
                this.itemMeta.getLore().add(current);
        } else {
            List<String> lore = new ArrayList<>(Arrays.asList(array));
            this.itemMeta.setLore(lore);
        }
        return this;
    }

    public ItemBuilder setType(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder setAmount(Integer amount) {
        this.itemStack.setAmount(amount.intValue());
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment) {
        this.itemMeta.addEnchant(enchantment, 1, false);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, Integer power) {
        this.itemMeta.addEnchant(enchantment, power.intValue(), false);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}


