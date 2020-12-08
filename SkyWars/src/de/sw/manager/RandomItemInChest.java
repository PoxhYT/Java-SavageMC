package de.sw.manager;

import io.netty.util.internal.ThreadLocalRandom;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RandomItemInChest {
    public final int ItemId;
    public final List<KitEnchantments> KitEnchantments;
    public final int Min;
    public final int Max;

    public final int Chance;

    public RandomItemInChest(int ItemId, List<KitEnchantments> KitEnchantments, int Min, int Max, int Chance) {
        this.ItemId = ItemId;
        this.KitEnchantments = KitEnchantments;
        this.Min = Min;
        this.Max = Max;
        this.Chance = Chance;
    }
}
