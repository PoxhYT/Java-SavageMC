package de.sw.listener;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitItem {
    private final String name;
    private final List<KitEnchantments> enchantments;
    private final String materialName;
    private final int amount;

    public KitItem(String name, List<KitEnchantments> enchantments, String materialName, int amount) {
        this.name = name;
        this.enchantments = enchantments;
        this.materialName = materialName;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public List<KitEnchantments> getEnchantments() {
        return enchantments;
    }

    public String getMaterialName() {
        return materialName;
    }

    public ItemStack getItem() {
        ItemStack i = new ItemStack(Material.getMaterial(materialName), amount);
        enchantments.forEach(ench -> {
            i.addEnchantment(Enchantment.getByName(ench.enchantmentName), ench.enchantmentLevel);
        });

        return i;
    }
}
