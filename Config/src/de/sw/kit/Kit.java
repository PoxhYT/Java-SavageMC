package de.sw.kit;

import org.bukkit.Material;

public class Kit {
    private String name;

    private String description;

    private Material slot1;

    private int coins;

    private String permission;

    public Kit(String name, String description, Material slot1, int coins, String permission) {
        this.name = name;
        this.description = description;
        this.slot1 = slot1;
        this.coins = coins;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Material getSlot1() {
        return slot1;
    }
}
