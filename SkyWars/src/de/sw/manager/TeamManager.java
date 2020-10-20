package de.sw.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TeamManager {

    private String teamName;
    private String teamPrefix;
    private int maxPlayers;
    public ArrayList<Player> players;
    public Material material;

    public TeamManager(String teamName, String teamPrefix, int teamSize, Material material) {
        this.teamName = teamName;
        this.teamPrefix = teamPrefix;
        this.maxPlayers = teamSize;
        this.players = new ArrayList<>();
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ArrayList<Player> getPlayers() {
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

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isInTeam(Player player) {
        if (players.contains(player.getName())) {
            return true;
        }
        return false;
    }
}
