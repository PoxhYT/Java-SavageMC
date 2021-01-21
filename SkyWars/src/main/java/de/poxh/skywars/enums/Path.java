package de.sw.enums;

public enum Path {
    Id("Id"),
    MapName("MapName"),
    MaxTeamCount("MaxTeamCount"),
    MapsIDs("MapsIDs"),
    StillUnderDevelopment("StillUnderDevelopment"),
    Locations("Locations"),
    MaxPlayersInTeam("MaxPlayersInTeam"),
    GameSize("GameSize"),
    MaxPlayers("Settings.MaxPlayers"),
    MinPlayers("Settings.MinPlayers"),
    Radius("Radius");
    private final String text;

    @Override
    public String toString() {
        return text;
    }

    Path(final String text) {
        this.text = text;
    }
}
