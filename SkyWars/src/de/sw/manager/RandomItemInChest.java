package de.sw.manager;

import io.netty.util.internal.ThreadLocalRandom;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RandomItemInChest {
    public final int ItemId;
    public final List<KitEnchantments> KitEnchantments;
    public final int Min;
    public final int Max;

    public RandomItemInChest(int ItemId, List<KitEnchantments> KitEnchantments, int Min, int Max) {
        this.ItemId = ItemId;
        this.KitEnchantments = KitEnchantments;
        this.Min = Min;
        this.Max = Max;
    }

    public static ItemStack getItemWithRange(int materialId, int min, int max,List<KitEnchantments> enchantments) {
        return getItem(materialId, ThreadLocalRandom.current().nextInt(min, max + 1), enchantments);
    }

    public static ItemStack getItem(int itemId, int amount, List<KitEnchantments> kitEnchantments) {
        return ItemDocument.getItem(itemId, amount, kitEnchantments);
    }
}
