package de.sw.manager;

public class SkyWarsMapData {
    public final String mapName;
    public final int maxTeamCount;
    public final int maxPlayersInTeam;
    public final String gameSize;
    public boolean stillUnderDevelopment;
    //public final Location[] location;

    public SkyWarsMapData(String mapName, String gameSize, Object o, int maxTeamCount, int maxPlayersInTeam, boolean stillUnderDevelopment) {
        //this.location = location;
        this.mapName = mapName;
        this.maxTeamCount = maxTeamCount;
        this.maxPlayersInTeam = maxPlayersInTeam;
        this.gameSize = gameSize;
        this.stillUnderDevelopment = stillUnderDevelopment;
    }
}
