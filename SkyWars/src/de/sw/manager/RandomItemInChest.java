package de.sw.manager;

import io.netty.util.internal.ThreadLocalRandom;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RandomItemInChest extends ItemDocument {
    public RandomItemInChest(int ItemId, List<KitEnchantments> KitEnchantments, int Amount) {
        super(ItemId, KitEnchantments, Amount);
    }

    public static ItemStack getItemWithRange(int materialId, int min, int max,List<KitEnchantments> enchantments) {
        return getItem(materialId, ThreadLocalRandom.current().nextInt(min, max + 1), enchantments);
    }

    public ItemStack getItem() {
        return ItemDocument.getItem(this.ItemId, this.Amount, this.KitEnchantments);
    }
}
