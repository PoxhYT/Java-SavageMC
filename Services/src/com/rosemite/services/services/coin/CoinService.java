package com.rosemite.services.services.coin;

import com.rosemite.services.services.http.Http;
import javafx.util.Pair;

public class CoinService {
    private Http http;

    public CoinService(Http http) {
        this.http = http;
    }

    public int getCoinAmount(String uuid) {
        // TODO: Return Total coins of Player
        return -1;
    }

    public int addCoins(String uuid) {
        // Todo: Add coins to player and return total coins
        return -1;
    }

    public Pair<Integer, Boolean> removeCoins(String uuid) {
        // TODO: Remove Coins from player ( Return total coins & Return a boolean if successful or not depending on if the user has enough coins )
        return null;
    }
}
