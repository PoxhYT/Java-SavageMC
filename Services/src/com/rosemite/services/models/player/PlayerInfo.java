package com.rosemite.services.models.player;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

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

    public String toJsonAsString() {
        Map<String, Object> json = new HashMap<>();
        json.put("hasPremium", hasPremium);
        json.put("playername", playername);
        json.put("uuid", uuid);
        json.put("coins", coins);

        return new Gson().toJson(json);
    }

    public Map<String, Object> toJsonAsMap() {
        Map<String, Object> json = new HashMap<>();
        json.put("hasPremium", hasPremium);
        json.put("playername", playername);
        json.put("uuid", uuid);
        json.put("coins", coins);

        return json;
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
