package com.rosemite.services.systems;

import com.rosemite.services.backend.http.Http;
import org.bukkit.entity.Player;

public class CoinSystem {

    private Http http;
    public CoinSystem(Http http) {
        this.http = http;
    }

    public void addCoins(Player player, int amount) {
        // Todo
    }

    public void removeCoins(Player player, int amount) {
        // Todo
    }
}
