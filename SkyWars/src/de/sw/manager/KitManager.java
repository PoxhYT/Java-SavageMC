package de.sw.manager;

import org.bukkit.Material;

public class KitManager {
    private final String kitName;
    private final String kitDescription;
    private final int kitIcon;
    private final int kitPrice;

    public KitManager(String kitName, String kitDescription, double kitIcon, double kitPrice) {
        int icon = (int)Math.round(kitIcon);
        this.kitIcon = icon;

        this.kitName = kitName;
        this.kitDescription = kitDescription;
        this.kitPrice = (int)Math.round(kitPrice);
    }

    public String getKitName() {
        return "§8• §e" + kitName;
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

    public String getKit() {
        return "§e" + kitName;
    }
}
