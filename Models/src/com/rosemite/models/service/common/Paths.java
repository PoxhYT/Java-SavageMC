package com.rosemite.models.service.common;

public enum Paths {
    SoupTraining("SoupTraining"),
    PlayerSkywarsKits("PlayerSkywarsKits"),
    DefaultSkywarsKits("DefaultSkywarsKits"),
    Report("ErrorReports"),
    PlayerSkywarsStats("PlayerSkywarsStats"),
    PlayerInfo("Players"),
    RewardInfo("Reward"),
    CityBuild("CityBuild"),
    Relationships("Relationships"),
    LobbyInfo("Lobby");
    public final String val;

    @Override
    public String toString() {
        return val;
    }
    Paths(final String text) {
        this.val = text;
    }
}
