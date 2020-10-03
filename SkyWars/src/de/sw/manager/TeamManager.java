package de.sw.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TeamManager {

    public List<Team> teams;
    public String teamName;
    public List<Player> playersInTeam;
    public Material material;
    public String playerPrefix;

    public TeamManager(String teamName, Material material, String playerPrefix) {
        this.teamName = teamName;
        this.material = material;
        this.playerPrefix = playerPrefix;
    }

    public String getTeamName() {
        return "§8• §7" + teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Player> getPlayersInTeam() {
        return playersInTeam;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public String getPlayerPrefix() {
        return playerPrefix;
    }

    public void setPlayerPrefix(String playerPrefix) {
        this.playerPrefix = playerPrefix;
    }

    public Material getMaterial() {
        return material;
    }
}
