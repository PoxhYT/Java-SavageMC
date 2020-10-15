package com.rosemite.services.models.player;

import java.util.HashMap;
import java.util.Map;

public class PlayerSkywarsKits {
    private Map<String, Object> playerKits;

    public PlayerSkywarsKits(Map<String, Object> playerKits) {
        if (playerKits == null) {
            this.playerKits = new HashMap<>();
            return;
        }

        this.playerKits = playerKits;
    }

    public boolean hasSkywarsKit(String kitName) {
        if (kitName.equals("Standard")) {
            return true;
        } else if (playerKits.get(kitName) == null) {
            return false;
        }

        return (boolean)playerKits.get(kitName);
    }

    // Todo: When player buys a kit. This Function needs to be called then.
    public void addKitToBought(String kitName) {
        playerKits.put(kitName, true);
    }
}
