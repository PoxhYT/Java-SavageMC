package de.sw.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TeamManager {

    private String teamName;
    private String teamPrefix;
    private int teamSize;
    public ArrayList<String> players;
    public ArrayList<Player> sizePlayers;

    public TeamManager(String teamName, String teamPrefix, int teamSize) {
        this.teamName = teamName;
        this.teamPrefix = teamPrefix;
        this.teamSize = teamSize;
        this.players = new ArrayList<>();
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamPrefix() {
        return teamPrefix;
    }

    public void setTeamPrefix(String teamPrefix) {
        this.teamPrefix = teamPrefix;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public ItemStack getIcon() {
        ItemStack itemStack = new ItemStack(Material.WOOL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(teamName);
        itemMeta.setLore(players);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
