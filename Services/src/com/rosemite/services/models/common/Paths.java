package com.rosemite.services.models.common;

public enum Paths {
    SoupTraining("/SoupTraining/"),
    SkywarsKits("/SkyWarsKits/"),
    Kits("/Kits/"),
    PlayerInfo("/Players/");
    private final String text;

    @Override
    public String toString() {
        return text;
    }

    Paths(final String text) {
        this.text = text;
    }
}
