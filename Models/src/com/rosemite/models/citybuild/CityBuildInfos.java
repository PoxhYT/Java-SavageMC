package com.rosemite.models.citybuild;

public class CityBuildInfos {

    private String playerName;
    private String displayName;
    private String UUID;
    private int coins;

    public CityBuildInfos(String playerName, String displayName, String UUID, int coins) {
        this.playerName = playerName;
        this.displayName = displayName;
        this.UUID = UUID;
        this.coins = coins;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
