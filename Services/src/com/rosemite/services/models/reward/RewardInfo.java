package com.rosemite.services.models.reward;

public class RewardInfo {

    private final String uuid;
    private final String displayName;
    private final String playerTicketReceive;
    private final boolean playerTicketReceived;
    private final String playerCoinsReceive;
    private final boolean playerCoinsReceived;
    private final String premiumTicketReceive;
    private final boolean premiumTicketReceived;
    private final String premiumCoinsReceive;
    private final boolean premiumCoinsReceived;
    private final String savageTicketReceive;
    private final boolean savageTicketReceived;
    private final String savageCoinsReceive;
    private final boolean savageCoinsReceived;

    public RewardInfo(String uuid, String displayName, String playerTicketReceive, boolean playerTicketReceived, String playerCoinsReceive, boolean playerCoinsReceived,
        String premiumTicketReceive, boolean premiumTicketReceived, String premiumCoinsReceive, boolean premiumCoinsReceived,
        String savageTicketReceive, boolean savageTicketReceived, String savageCoinsReceive, boolean savageCoinsReceived) {
        this.uuid = uuid;
        this.displayName = displayName;
        this.playerTicketReceive = playerTicketReceive;
        this.playerTicketReceived = playerTicketReceived;
        this.playerCoinsReceive = playerCoinsReceive;
        this.playerCoinsReceived = playerCoinsReceived;
        this.premiumTicketReceive = premiumTicketReceive;
        this.premiumTicketReceived = premiumTicketReceived;
        this.premiumCoinsReceive = premiumCoinsReceive;
        this.premiumCoinsReceived = premiumCoinsReceived;
        this.savageTicketReceive = savageTicketReceive;
        this.savageTicketReceived = savageTicketReceived;
        this.savageCoinsReceive = savageCoinsReceive;
        this.savageCoinsReceived = savageCoinsReceived;
    }

    public String getPlayerTicketReceive() {
        return playerTicketReceive;
    }

    public boolean isPlayerTicketReceived() {
        return playerTicketReceived;
    }

    public String getPlayerCoinsReceive() {
        return playerCoinsReceive;
    }

    public boolean isPlayerCoinsReceived() {
        return playerCoinsReceived;
    }

    public String getPremiumTicketReceive() {
        return premiumTicketReceive;
    }

    public boolean isPremiumTicketReceived() {
        return premiumTicketReceived;
    }

    public String getPremiumCoinsReceive() {
        return premiumCoinsReceive;
    }

    public boolean isPremiumCoinsReceived() {
        return premiumCoinsReceived;
    }

    public String getSavageTicketReceive() {
        return savageTicketReceive;
    }

    public boolean isSavageTicketReceived() {
        return savageTicketReceived;
    }

    public String getSavageCoinsReceive() {
        return savageCoinsReceive;
    }

    public boolean isSavageCoinsReceived() {
        return savageCoinsReceived;
    }
}
