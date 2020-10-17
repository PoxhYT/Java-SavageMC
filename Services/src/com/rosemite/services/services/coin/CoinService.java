package com.rosemite.services.services.coin;

import com.rosemite.services.main.MainService;
import com.rosemite.services.models.common.Path;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.models.http.HttpResponse;
import com.rosemite.services.models.http.HttpType;
import com.rosemite.services.models.player.PlayerInfo;
import com.rosemite.services.services.http.Http;
import javafx.util.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CoinService {
    private Http http;
    private MainService mainService;

    public CoinService(Http http, MainService mainService) {
        this.http = http;
        this.mainService = mainService;
    }

    public int getCoinAmount(PlayerInfo player) {
        return player.getCoins();
    }

    public int addCoins(PlayerInfo player, int amount) {
        Map<String, Object> body = new HashMap<>();

        body.put("coins", player.getCoins() + amount);

        Path path = new Path(
            Paths.PlayerInfo,
            player.getUuid()
        );

        try {
            HttpResponse res = http.request(HttpType.POST,body, path);

            if (res.statusCode != 200) {
                http.reportError(res.content);
                return player.getCoins();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return player.getCoins();
        }

        return player.getCoins() + amount;
    }

    public Pair<Integer, Boolean> removeCoins(PlayerInfo player, int amount) {
        if (player.getCoins() - amount < 0) {
            return new Pair<>(amount, false);
        }

        Map<String, Object> body = new HashMap<>();

        body.put("coins", player.getCoins() - amount);

        Path path = new Path(
            Paths.PlayerInfo,
            player.getUuid()
        );

        try {
            HttpResponse res = http.request(HttpType.POST,body, path);

            if (res.statusCode != 200) {
                http.reportError(res.content);
                return new Pair<>(player.getCoins() - amount, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Pair<>(player.getCoins() - amount, true);
        }

        return new Pair<>(player.getCoins() - amount, true);
    }
}
