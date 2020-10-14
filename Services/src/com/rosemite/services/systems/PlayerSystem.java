package com.rosemite.services.systems;

import com.rosemite.services.backend.http.Http;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerSystem {
    private Http http;

    public PlayerSystem(Http http) {
        this.http = http;
    }

    public void getPlayerInfo(UUID uuid) {
        // TODO: Get Player Infos like => hasPremium, ...
    }
}
