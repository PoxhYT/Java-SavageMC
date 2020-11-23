package de.sw.manager;

import de.sw.main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class TeamManager {

    private String teamName;
    private String teamPrefix;
    public ArrayList<Player> players;
    public Material material;
    private boolean isAlive = true;
    public ArrayList<UUID> spectators = new ArrayList<>();
    public final int maxPlayerCount;
    public final Location location;

    public TeamManager(String teamName, String teamPrefix, Location location, Material material, int maxPlayerCount) {
        this.location = location;
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
        return maxPlayerCount == players.size();
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

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public ArrayList<UUID> getSpectators() {
        return spectators;
    }

    public boolean getAlive() {
        return isAlive;
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
        for (Player value : players) {
            if (value.getUniqueId() == player.getUniqueId()) {
                return true;
            }
        }
        return false;
    }

    public void teleportPlayers() {
        players.forEach(player -> player.teleport(this.location));
    }
}
