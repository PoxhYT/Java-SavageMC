package de.sw.manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class SkywarsMapData {
    private Plugin plugin;

    private final String mapId;
    private final String mapName;
    private final int maxTeamCount;
    private final int maxPlayersInTeam;

    private int joinedPlayerCount;

    public SkywarsMapData(String mapId) {
        // Load map data from config.yml
        plugin = Bukkit.getPluginManager().getPlugin("Skywars");
        this.mapId = mapId + ".";

        this.mapName = (String)get("mapName");
        this.maxTeamCount = (int)get("maxTeamCount");
        this.maxPlayersInTeam = (int)get("maxPlayersInTeam");
    }

    private Object get(String path) {
        return plugin.getConfig().get(mapId+path);
    }
}
