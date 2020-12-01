package de.sw.manager;

import io.netty.util.internal.ThreadLocalRandom;
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

    public static ItemStack getItem(int materialId, int amount, List<KitEnchantments> enchantments) {
        ItemStack i = new ItemStack(Material.getMaterial(materialId), amount);
        ItemMeta meta = i.getItemMeta();

        if (enchantments == null) { return i; }

        enchantments.forEach(enchantment ->
            meta.addEnchant(Enchantment.getById(enchantment.EnchantmentId),
            enchantment.EnchantmentLevel,
            true)
        );
        i.setItemMeta(meta);

        return i;
    }
}
