package de.sw.enums;

public enum Path {
    MapName("MapName"),
    TeamCount("TeamCount"),
    MapsIDs("MapsIDs"),
    MaxPlayersInTeam("MaxPlayersInTeam");
    private final String text;

    @Override
    public String toString() {
        return text;
    }

    Path(final String text) {
        this.text = text;
    }
}
