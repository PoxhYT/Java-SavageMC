package com.rosemite.services.main;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rosemite.services.service.report.ReportService;
import com.rosemite.services.services.coin.CoinService;
import com.rosemite.services.services.player.PlayerService;
import com.rosemite.services.services.skywars.SkywarsService;
import com.rosemite.services.services.souptraining.SoupTrainingService;
import org.bson.Document;

public class ServiceHolder {
    private final SoupTrainingService soupTrainingService;
    private final SkywarsService skywarsService;
    private final PlayerService playerService;
    private final CoinService coinService;
    private final ReportService reportService;

    public ServiceHolder(MongoDatabase db, MainService service) {
        this.soupTrainingService = new SoupTrainingService(db, service);
        this.skywarsService = new SkywarsService(db, service);
        this.playerService = new PlayerService(db, service);
        this.coinService = new CoinService(db, service);
        this.reportService = new ReportService(db);
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
    public ReportService getReportService() {
        return reportService;
    }
}
