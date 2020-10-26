package com.rosemite.services.models.player;

public class PlayerInfo {
    private final boolean hasPremium;
    private final String playername;
    private final String latestSelectedKit;
    private final String uuid;
    private final int coins;

    public PlayerInfo(boolean hasPremium, String playername, String latestSelectedKit, String uuid, int coins) {
        this.latestSelectedKit = latestSelectedKit;
        this.hasPremium = hasPremium;
        this.playername = playername;
        this.uuid = uuid;
        this.coins = coins;
    }

    public boolean hasPremium() {
        return hasPremium;
    }

    public String getLatestSelectedKit() {
        return latestSelectedKit;
    }
    public String getPlayername() {
        return playername;
    }

    public String getUuid() {
        return uuid;
    }

    public int getCoins() {
        return coins;
    }
}
