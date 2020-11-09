package de.sw.manager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    public HashMap<UUID, Integer> kills = new HashMap<>();
    private Player player;

    public PlayerManager(Player player) {
        this.player = player;
    }

    public int getKills() {
        return ((Integer)kills.getOrDefault(this.player.getUniqueId(), Integer.valueOf(0))).intValue();
    }

    public void addKills() {
        kills.put(this.player.getUniqueId(), Integer.valueOf(getKills() + 1));
    }

    public Player getPlayer() {
        return player;
    }
}
