package com.rosemite.models.service.common;

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

public interface IService {
    SoupTrainingService getSoupTrainingService();
    SkywarsService getSkywarsService();
    TicketService getTicketService();
    ReportService getReportService();
    PlayerService getPlayerService();
    CoinService getCoinService();
    LobbyService getLobbyService();
    FriendsService getFriendsService();
    RewardService getRewardService();
    CityBuildService getCityBuildService();
}
