package de.sw.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsBuilder {
    private ItemStack itemStack;

    private ItemMeta itemMeta;

    private List<String> lore = new ArrayList<>();

    public ItemsBuilder(Material material, int amount, int subid) {
        this.itemStack = new ItemStack(material, amount, (short)subid);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemsBuilder setName(String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    public ItemsBuilder setNoName() {
        this.itemMeta.setDisplayName("§f");
        return this;
    }

    public ItemsBuilder addLore(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemsBuilder setEnchatment(Enchantment enchatment, int level, boolean value) {
        this.itemMeta.addEnchant(enchatment, level, value);
        return this;
    }

    public ItemsBuilder setUnbreakble() {
        this.itemMeta.spigot().setUnbreakable(true);
        return this;
    }

    public ItemStack build() {
        if (this.lore == null && this.lore.isEmpty()) {
            this.itemStack.setItemMeta(this.itemMeta);
            return this.itemStack;
        }
        this.itemMeta.setLore(this.lore);
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}
