package com.rosemite.services.skywars;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

public class ItemDocument {
    public final int ItemId;
    public final int Amount;
    public final List<KitEnchantments> KitEnchantments;

    public ItemDocument(int ItemId, List<KitEnchantments> KitEnchantments, int Amount) {
        this.ItemId = ItemId;
        this.Amount = Amount;
        this.KitEnchantments = KitEnchantments;
    }

    public static ItemStack getItem(int materialId, int amount, List<KitEnchantments> enchantments) { // TODO: Fix this
        ItemStack i = new ItemStack(Material.STONE, amount);
        ItemMeta meta = i.getItemMeta();

//        i.getEnchantments().forEach((x, y) -> {
//            x.id
//        });

        if (enchantments == null) { return i; }

        enchantments.forEach(enchantment ->
            meta.addEnchant(Enchantment.WATER_WORKER,
            enchantment.EnchantmentLevel,
            true)
        );
        i.setItemMeta(meta);

        return i;
    }
}
