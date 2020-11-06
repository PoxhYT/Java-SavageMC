package de.sw.manager;

import org.bukkit.Location;

public class SkyWarsMapData {
    public final String mapName;
    public final int maxTeamCount;
    public final int maxPlayersInTeam;
    public final String gameSize;
    public final Location[] location;

    public SkyWarsMapData(String mapName, String gameSize, Location[] location, int maxTeamCount, int maxPlayersInTeam) {
        this.location = location;
        this.mapName = mapName;
        this.maxTeamCount = maxTeamCount;
        this.maxPlayersInTeam = maxPlayersInTeam;
        this.gameSize = gameSize;
    }
}
