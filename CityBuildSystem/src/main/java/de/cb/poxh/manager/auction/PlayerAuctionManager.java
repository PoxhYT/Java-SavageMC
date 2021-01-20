package de.cb.poxh.manager.auction;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class PlayerAuctionManager {

    private String seller;
    private String buyer;
    private ItemStack itemSell;
    private int price;
    private String UUID;

    public PlayerAuctionManager(String seller, ItemStack itemSell, int price, String UUID) {
        this.seller = seller;
        this.itemSell = itemSell;
        this.price = price;
        this.UUID = UUID;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setItemSell(ItemStack itemSell) {
        this.itemSell = itemSell;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUUID() {
        return UUID;
    }

    public void setLore(String... lore) {
        ItemMeta meta = itemSell.getItemMeta();
        meta.setLore(Arrays.asList(lore));
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public ItemStack getItemSell() {
        return itemSell;
    }
}
