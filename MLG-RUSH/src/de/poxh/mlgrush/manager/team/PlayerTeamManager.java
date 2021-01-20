package de.poxh.mlgrush.manager.team;


import org.bukkit.inventory.ItemStack;

public class PlayerTeamManager {

    private final String teamName;
    private final ItemStack teamItem;

    public PlayerTeamManager(String teamName, ItemStack teamItem) {
        this.teamName = teamName;
        this.teamItem = teamItem;
    }

    public String getTeamName() {
        return teamName;
    }

    public org.bukkit.inventory.ItemStack getTeamItem() {
        return teamItem;
    }
}
