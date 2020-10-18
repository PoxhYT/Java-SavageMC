package com.rosemite.services.main;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rosemite.services.services.coin.CoinService;
import com.rosemite.services.services.http.Http;
import com.rosemite.services.services.player.PlayerService;
import com.rosemite.services.services.skywars.SkywarsService;
import com.rosemite.services.services.souptraining.SoupTrainingService;
import org.bson.Document;

public class ServiceHolder {
    private final SoupTrainingService soupTrainingService;
    private final SkywarsService skywarsService;
    private final PlayerService playerService;
    private final CoinService coinService;

    public ServiceHolder(MongoDatabase db,MainService service) {
        this.soupTrainingService = new SoupTrainingService(db);
        this.skywarsService = new SkywarsService(db);
        this.playerService = new PlayerService(db);
        this.coinService = new CoinService(db, service);
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
