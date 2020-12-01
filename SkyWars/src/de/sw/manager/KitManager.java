package de.sw.manager;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class KitManager {
    private final String kitName;
    private final String kitDescription;
    private final int kitIcon;
    private final int kitPrice;
    private List<KitItem> kitItems;
    private List<ItemDocument> Items;

    private boolean hasKit = false;

    public KitManager(String kitName, String kitDescription, List<KitItem> kitItems, double kitIcon, double kitPrice) {
        this.kitIcon = (int)Math.round(kitIcon);

        this.kitItems = kitItems;
        this.kitName = kitName;
        this.kitDescription = kitDescription;
        this.kitPrice = (int)Math.round(kitPrice);
    }

    public List<ItemDocument> getItems() {
        return Items;
    }

    public String getKitNameLiteralString() {
        return kitName;
    }

    public String getKitNameLiteralStringColored() {
        return "§e" + kitName;
    }

    public void setHasKit(boolean hasKit) {
        this.hasKit = hasKit;
    }

    public String[] getKitDescription() {
        return kitDescription.split(",");
    }

    public Material getKitIcon() {
        return Material.getMaterial(kitIcon);
    }

    public int getKitPrice() {
        return kitPrice;
    }

    public boolean getHasKit() {
        return hasKit;
    }

    public String getKitName() {
        if (hasKit) {
            return "§e" + kitName + " §8[§aGekauft§8]";
        }
        return "§e" + kitName + "§8[Nicht §cGekauft§8]";
    }

    public List<KitItem> getKitItem() {
        return kitItems;
    }

    public void addKitItem(KitItem kitItem) {
        if (kitItems == null) {
            kitItems = new ArrayList<>();
        }

        this.kitItems.add(kitItem);
    }
}
