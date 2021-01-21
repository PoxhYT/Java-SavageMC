package com.rosemite.services.holder;

import com.mongodb.client.MongoDatabase;
import com.rosemite.models.service.common.IService;
import com.rosemite.services.citybuild.CityBuildService;
import com.rosemite.services.coin.CoinService;
import com.rosemite.services.friends.FriendsService;
import com.rosemite.services.lobby.LobbyService;
import com.rosemite.services.player.PlayerService;
import com.rosemite.services.report.ReportService;
import com.rosemite.services.reward.RewardService;
import com.rosemite.services.skywars.SkywarsService;
import com.rosemite.services.souptraining.SoupTrainingService;
import com.rosemite.services.ticket.TicketService;

public class ServiceHolder {
    private final SoupTrainingService soupTrainingService;
    private final SkywarsService skywarsService;
    private final PlayerService playerService;
    private final CoinService coinService;
    private final ReportService reportService;
    private final TicketService ticketService;
    private final LobbyService lobbyService;
    private final FriendsService friendsService;
    private final RewardService rewardService;
    private final CityBuildService cityBuildService;

    public ServiceHolder(MongoDatabase db, IService service) {
        this.soupTrainingService = new SoupTrainingService(db, service);
        this.cityBuildService = new CityBuildService(db, service);
        this.skywarsService = new SkywarsService(db, service);
        this.playerService = new PlayerService(db, service);
        this.friendsService = new FriendsService(db, service);
        this.lobbyService = new LobbyService(db, service);
        this.ticketService = new TicketService(db, service);
        this.coinService = new CoinService(db, service);
        this.reportService = new ReportService(db);
        this.rewardService = new RewardService(db, service);
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

    public TicketService getTicketService() {
        return ticketService;
    }

    public LobbyService getLobbyService() {
        return lobbyService;
    }

    public FriendsService getFriendsService() {
        return friendsService;
    }

    public RewardService getRewardService() {
        return rewardService;
    }

    public CityBuildService getCityBuildService() {
        return cityBuildService;
    }
}
