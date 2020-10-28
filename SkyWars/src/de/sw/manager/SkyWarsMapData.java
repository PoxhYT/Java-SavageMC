package de.sw.manager;

import de.sw.enums.Path;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SkyWarsMapData {
    private final YamlConfiguration yml;

    public final String query;
    public final String mapName;
    public final int maxTeamCount;
    public final int maxPlayersInTeam;

    private int joinedPlayerCount; // This can be useful when showing it to the join Sign

    public SkyWarsMapData(YamlConfiguration yml, String mapId) {
        this.yml = yml;
        this.query = Path.MapsIDs.toString() + "." + mapId + ".";

        this.mapName = (String)get(Path.MapName.toString());
        this.maxTeamCount = (int)get(Path.TeamCount.toString());
        this.maxPlayersInTeam = (int)get(Path.MaxPlayersInTeam.toString());
    }

    private Object get(String path) { return yml.get(query + path); }
}
