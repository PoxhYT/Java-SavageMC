package com.rosemite.services.models.player;

public class PlayerInfo {
    private final boolean hasPremium;
    private final String playername;
    private final String latestSelectedSkywarsKit;
    private final String uuid;
    private final int coins;
    private final int tickets;

    public PlayerInfo(boolean hasPremium, String playername, String latestSelectedSkywarsKit, String uuid, int coins, int tickets) {
        this.latestSelectedSkywarsKit = latestSelectedSkywarsKit;
        this.hasPremium = hasPremium;
        this.playername = playername;
        this.uuid = uuid;
        this.coins = coins;
        this.tickets = tickets;
    }

    public boolean hasPremium() {
        return hasPremium;
    }

    public String getLatestSelectedKit() {
        return latestSelectedSkywarsKit;
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

    public int getTickets() {
        return tickets;
    }
}
