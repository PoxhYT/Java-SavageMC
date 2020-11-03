package de.sw.manager;

import de.sw.enums.Path;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class SkyWarsMapData {
    public final String mapName;
    public final int maxTeamCount;
    public final int maxPlayersInTeam;
    public final String gameSize;

    private int joinedPlayerCount; // This can be useful when showing it to the join Sign

    public SkyWarsMapData(String mapName, String gameSize, int maxTeamCount, int maxPlayersInTeam) {
        this.mapName = mapName;
        this.maxTeamCount = maxTeamCount;
        this.maxPlayersInTeam = maxPlayersInTeam;
        this.gameSize = gameSize;
    }
}
