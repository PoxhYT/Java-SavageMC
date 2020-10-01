package de.sw.manager;

import org.bukkit.Material;

public class KitManager {

    private String kitName;
    private String kitDescription;
    private Material kitIcon;
    private int kitPrice;

    public KitManager(String kitName, String kitDescription, Material kitIcon, int kitPrice) {
        this.kitName = kitName;
        this.kitDescription = kitDescription;
        this.kitIcon = kitIcon;
        this.kitPrice = kitPrice;
    }

    public String getKitName() {
        return "§8• §e" + kitName;
    }

    public void setKitName(String kitName) {
        this.kitName = kitName;
    }

    public String getKitDescription() {
        return kitDescription;
    }

    public void setKitDescription(String kitDescription) {
        this.kitDescription = kitDescription;
    }

    public Material getKitIcon() {
        return kitIcon;
    }

    public void setKitIcon(Material kitIcon) {
        this.kitIcon = kitIcon;
    }

    public int getKitPrice() {
        return kitPrice;
    }

    public void setKitPrice(int kitPrice) {
        this.kitPrice = kitPrice;
    }
}
