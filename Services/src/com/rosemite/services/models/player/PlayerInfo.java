package com.rosemite.services.models.player;

public class PlayerInfo {
    private boolean hasPremium;
    private final String playername;
    private final String uuid;
    private int coins;

    public PlayerInfo(boolean hasPremium, String playername, String uuid, int coins) {
        this.hasPremium = hasPremium;
        this.playername = playername;
        this.uuid = uuid;
        this.coins = coins;
    }

    public boolean isHasPremium() {
        return hasPremium;
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
