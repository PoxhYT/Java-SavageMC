package com.rosemite.services.main;

import com.rosemite.services.services.coin.CoinService;
import com.rosemite.services.services.http.Http;
import com.rosemite.services.services.player.PlayerService;
import com.rosemite.services.services.skywars.SkywarsServices;
import com.rosemite.services.services.souptraining.SoupTrainingService;

public class ServiceHolder {
    private final SoupTrainingService soupTrainingService;
    private final SkywarsServices skywarsServices;
    private final PlayerService playerService;
    private final CoinService coinService;

    public ServiceHolder(Http http) {
        this.soupTrainingService = new SoupTrainingService(http);
        this.skywarsServices = new SkywarsServices(http);
        this.playerService = new PlayerService(http);
        this.coinService = new CoinService(http);
    }

    public SoupTrainingService getSoupTrainingService() {
        return soupTrainingService;
    }

    public SkywarsServices getSkywarsServices() {
        return skywarsServices;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public CoinService getCoinService() {
        return coinService;
    }
}
