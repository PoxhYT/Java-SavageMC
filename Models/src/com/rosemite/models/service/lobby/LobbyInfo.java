package com.rosemite.models.service.lobby;

public class LobbyInfo {
    private final boolean spawnsSet;
    private final boolean villagerSet;
    private final int onlinePlayers;

    public LobbyInfo(boolean spawnsSet, boolean villagerSet, int onlinePlayers) {
        this.spawnsSet = spawnsSet;
        this.villagerSet = villagerSet;
        this.onlinePlayers = onlinePlayers;

    }

    public boolean isSpawnsSet() {
        return spawnsSet;
    }

    public boolean isVillagerSet() {
        return villagerSet;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }
}
