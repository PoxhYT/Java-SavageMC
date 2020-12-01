package de.sw.manager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class KitItem {
    private final String name;
    private final List<KitEnchantments> enchantments;
    private final int materialId;
    private final int amount;

    public KitItem(String name, List<KitEnchantments> enchantments, int materialId, int amount) {
        this.name = name;
        this.enchantments = enchantments;
        this.materialId = materialId;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public List<KitEnchantments> getEnchantments() {
        return enchantments;
    }

    public int getMaterialName() {
        return materialId;
    }

    public ItemStack getItem() {
        return ItemDocument.getItem(materialId, amount, enchantments);
    }
}