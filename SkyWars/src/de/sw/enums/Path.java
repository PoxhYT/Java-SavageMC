package de.sw.enums;

public enum Path {
    MapName("MapName"),
    MaxTeamCount("MaxTeamCount"),
    MapsIDs("MapsIDs"),
    TeamLocations("TeamLocations"),
    MaxPlayersInTeam("MaxPlayersInTeam"),
    GameSize("GameSize");
    private final String text;

    @Override
    public String toString() {
        return text;
    }

    Path(final String text) {
        this.text = text;
    }
}
