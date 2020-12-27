package com.rosemite.models.service.common;

public enum Paths {
    SoupTraining("SoupTraining"),
    PlayerSkywarsKits("PlayerSkywarsKits"),
    DefaultSkywarsKits("DefaultSkywarsKits"),
    Report("ErrorReports"),
    PlayerSkywarsStats("PlayerSkywarsStats"),
    PlayerInfo("Players"),
    LobbyInfo("Lobby");
    private final String value;

    @Override
    public String toString() {
        return value;
    }
    Paths(final String text) {
        this.value = text;
    }
}
