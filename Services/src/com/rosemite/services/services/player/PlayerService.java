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
import java.util.Map;

public class PlayerService {
    private Http http;

    public PlayerService(Http http) {
        this.http = http;
    }

    public PlayerInfo fetchPlayer(String uuid) {
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

    public PlayerSkywarsKits fetchPlayerSkywarsKits(String uuid) {
        try {
            HttpResponse res = http.request
            (
                HttpType.GET,
                null,
                new Path(Paths.PlayerInfo,
                uuid,
                Paths.Kits,
                "SkyWars")
            );

            if (res.statusCode != 200) {
                http.reportError(res.content);
                return null;
            }

            String json = new Gson().toJson(res.getAsMap().get("data"));

            Log.d(json);

            Log.d("Here");
            Log.d("Here");
            Log.d("Here");
            Log.d("Here");
            Log.d("Here");
            Log.d("Here");

            return new PlayerSkywarsKits(new Gson().fromJson(json, Map.class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
