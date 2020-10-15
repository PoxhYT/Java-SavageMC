package com.rosemite.services.models.player;

import java.util.Map;

public class PlayerSkywarsKits {
    Map<String, Object> playerKits;

    public PlayerSkywarsKits(Map<String, Object> playerKits) {
        this.playerKits = playerKits;
    }

    public boolean hasSkywarsKit(String kitName) {
        if (playerKits.get(kitName) == null) {
            return false;
        }

        return (boolean)playerKits.get(kitName);
    }

    public void addKitToBought(String kitName) {
        playerKits.put(kitName, true);
    }
}
