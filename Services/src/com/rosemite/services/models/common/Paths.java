package com.rosemite.services.models.common;

public enum Paths {
    SoupTraining("SoupTraining"),
    PlayerSkywarsKits("PlayerSkywarsKits"),
    DefaultSkywarsKits("DefaultSkywarsKits"),
    Report("ErrorReports"),
    PlayerInfo("Players");
    private final String text;

    @Override
    public String toString() {
        return text;
    }

    Paths(final String text) {
        this.text = text;
    }
}
