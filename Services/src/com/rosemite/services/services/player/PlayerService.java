package com.rosemite.services.services.player;

import com.google.gson.Gson;
import com.rosemite.services.helper.Log;
import com.rosemite.services.models.common.Path;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.models.http.HttpResponse;
import com.rosemite.services.models.player.PlayerInfo;
import com.rosemite.services.models.player.PlayerSkywarsKits;
import com.rosemite.services.services.http.Http;
import com.rosemite.services.models.http.HttpType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerService {
    private Map<String, PlayerInfo> players;
    private Http http;

    public PlayerService(Http http) {
        this.players = new HashMap<>();
        this.http = http;
    }

    public PlayerInfo getPlayerInfo(String uuid) {
        if (players.get(uuid) == null) {
            PlayerInfo player = fetchPlayer(uuid);

            if (player != null) {
                players.put(uuid, player);
            }
            return player;
        }

        return players.get(uuid);
    }

    private PlayerInfo fetchPlayer(String uuid) {
        try {
            HttpResponse res = http.request(HttpType.GET,null, new Path(Paths.PlayerInfo, uuid));

            if (res.statusCode != 200) {
                http.reportError(res.content);
                return null;
            }

            String json = new Gson().toJson(res.getAsMap().get("data"));

            return new Gson().fromJson(json, PlayerInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PlayerSkywarsKits fetchPlayerSkywarsKits(UUID uuid) {
        try {
            Path path = new Path(
                Paths.PlayerInfo,
                uuid.toString(),
                Paths.Kits,
                "SkyWars"
            );

            HttpResponse res = http.request(
                HttpType.GET,
                null,
                path
            );

            if (res.statusCode != 200) {
                Log.d(res.content);
                http.reportError(res.content);
                return null;
            }

            String json = new Gson().toJson(res.getAsMap().get("data"));

            return new PlayerSkywarsKits(new Gson().fromJson(json, Map.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
