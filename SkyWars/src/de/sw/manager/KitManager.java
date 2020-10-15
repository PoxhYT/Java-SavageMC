package de.sw.manager;

import org.bukkit.Material;

public class KitManager {
    private final String kitName;
    private final String kitDescription;
    private final int kitIcon;
    private final int kitPrice;

    private boolean hasKit = false;

    public KitManager(String kitName, String kitDescription, double kitIcon, double kitPrice) {
        this.kitIcon = (int)Math.round(kitIcon);

        this.kitName = kitName;
        this.kitDescription = kitDescription;
        this.kitPrice = (int)Math.round(kitPrice);
    }

    public String getKitNameLiteralString() {
        return kitName;
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

    public String getKitName() {
        if (hasKit) {
            return "§e" + kitName + " §8[§aGekauft§8]";
        }
        return "§e" + kitName + "§8[Nicht §cGekauft§8]";
    }
}
