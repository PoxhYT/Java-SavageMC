package de.sw.manager;


import de.sw.listener.KitEnchantments;

import java.util.List;

public class ItemDocument {
    public final int ItemId;
    public final int Amount;
    public final List<KitEnchantments> KitEnchantments;

    public ItemDocument(int ItemId, List<KitEnchantments> KitEnchantments, int Amount) {
        this.ItemId = ItemId;
        this.Amount = Amount;
        this.KitEnchantments = KitEnchantments;
    }
}
