package de.sw.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TeamManager {

    private String teamName;
    private String teamPrefix;
    public ArrayList<Player> players;
    public Material material;

    public final int maxPlayerCount;

    public TeamManager(String teamName, String teamPrefix, Material material, int maxPlayerCount) {
        this.teamName = teamName;
        this.teamPrefix = teamPrefix;
        this.players = new ArrayList<>();
        this.material = material;
        this.maxPlayerCount = maxPlayerCount;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public boolean isFull() {
        if (maxPlayerCount == players.size()) {
            return true;
        }

        return false;
    }

    public Material getMaterial() {
        return material;
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

    public void removePlayer(Player player) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUniqueId() == player.getUniqueId()) {
                players.remove(i);
                break;
            }
        }
    }

    public boolean isInTeam(Player player) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUniqueId() == player.getUniqueId()) {
                return true;
            }
        }
        return false;
    }
}
