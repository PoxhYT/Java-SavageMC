package com.rosemite.services.main;

import com.rosemite.services.services.coin.CoinService;
import com.rosemite.services.services.http.Http;
import com.rosemite.services.services.player.PlayerService;
import com.rosemite.services.services.skywars.SkywarsService;
import com.rosemite.services.services.souptraining.SoupTrainingService;

public class ServiceHolder {
    private final SoupTrainingService soupTrainingService;
    private final SkywarsService skywarsService;
    private final PlayerService playerService;
    private final CoinService coinService;

    public ServiceHolder(Http http, MainService service) {
        this.soupTrainingService = new SoupTrainingService(http);
        this.skywarsService = new SkywarsService(http);
        this.playerService = new PlayerService(http);
        this.coinService = new CoinService(http, service);
    }

    public SoupTrainingService getSoupTrainingService() {
        return soupTrainingService;
    }

    public SkywarsService getSkywarsService() {
        return skywarsService;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public CoinService getCoinService() {
        return coinService;
    }
}
