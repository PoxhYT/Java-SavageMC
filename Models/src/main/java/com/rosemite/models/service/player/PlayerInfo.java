package com.rosemite.models.service.player;

import java.util.List;

public class PlayerInfo {
    private final boolean hasPremium;
    private final boolean isBanned;
    private final String playername;
    private final String uuid;
    private final String latestSelectedSkywarsKit;
    private final int coins;
    private final int tickets;
    private final int bans;
    private final String endBanDate;
    private final String lastSeen;
    private final String receiveReward;
    private final boolean isOnline;
    private final String serverName;
    private final boolean allowSendFriendRequests;
    private final boolean allowSendPartyRequests;

    public PlayerInfo(boolean hasPremium, boolean isBanned, String playername, String latestSelectedSkywarsKit, String uuid, int coins, int tickets,
        int bans, String endBanDate, String receiveReward, String lastSeen, boolean isOnline, String serverName, boolean allowSendFriendRequests, boolean allowSendPartyRequests) {
        this.latestSelectedSkywarsKit = latestSelectedSkywarsKit;
        this.hasPremium = hasPremium;
        this.isBanned = isBanned;
        this.playername = playername;
        this.uuid = uuid;
        this.coins = coins;
        this.tickets = tickets;
        this.bans = bans;
        this.endBanDate = endBanDate;
        this.receiveReward = receiveReward;
        this.lastSeen = lastSeen;
        this.isOnline = isOnline;
        this.serverName = serverName;
        this.allowSendFriendRequests = allowSendFriendRequests;
        this.allowSendPartyRequests = allowSendPartyRequests;
    }

    public boolean isAllowSendFriendRequests() {
        return allowSendFriendRequests;
    }

    public boolean isAllowSendPartyRequests() {
        return allowSendPartyRequests;
    }

    public boolean hasPremium() {
        return hasPremium;
    }

    public boolean isBanned() {
        return isBanned;
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

    public int getBans() {
        return bans;
    }

    public String getServerName() {
        return serverName;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public String getEndBanDate() {
        return endBanDate;
    }
}
