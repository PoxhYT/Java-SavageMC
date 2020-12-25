package de.poxh.services.models.common;

public enum Paths {
    SoupTraining("SoupTraining"),
    PlayerSkywarsKits("PlayerSkywarsKits"),
    DefaultSkywarsKits("DefaultSkywarsKits"),
    Report("ErrorReports"),
    PlayerSkywarsStats("PlayerSkywarsStats"),
    PlayerInfo("Players"),
    LobbyInfo("Lobby");
    private final String text;

    @Override
    public String toString() {
        return text;
    }

    Paths(final String text) {
        this.text = text;
    }
}