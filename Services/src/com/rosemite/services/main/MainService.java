package com.rosemite.services.main;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.rosemite.helper.Log;
import com.rosemite.models.service.common.IService;
import com.rosemite.services.coin.CoinService;
import com.rosemite.services.friends.FriendsService;
import com.rosemite.services.holder.ServiceHolder;
import com.rosemite.services.config.Config;
import com.rosemite.services.lobby.LobbyService;
import com.rosemite.services.player.PlayerService;
import com.rosemite.services.report.ReportService;
import com.rosemite.services.reward.RewardService;
import com.rosemite.services.skywars.SkywarsService;
import com.rosemite.services.souptraining.SoupTrainingService;
import com.rosemite.services.ticket.TicketService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MainService extends JavaPlugin implements IService {
    private ServiceHolder holder;

    @Override
    public void onEnable() {
        // Initialize Config data
        Config config = new Config(this);

        // Get Credentials & Connect to Mongodb
        String connectionString = config.getConfiguration("backend.url").toString();

        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase db = mongoClient.getDatabase("savagemc");

        // Initialize Services
        holder = new ServiceHolder(db,this);

        Log.d("Loaded Services Successfully");
    }

    @Override
    public SoupTrainingService getSoupTrainingService() {
        return holder.getSoupTrainingService();
    }

    @Override
    public SkywarsService getSkywarsService() {
        return holder.getSkywarsService();
    }

    @Override
    public TicketService getTicketService() {
        return holder.getTicketService();
    }

    @Override
    public CoinService getCoinService() {
        return holder.getCoinService();
    }

    @Override
    public ReportService getReportService() {
        return holder.getReportService();
    }

    @Override
    public PlayerService getPlayerService() {
        return holder.getPlayerService();
    }

    @Override
    public LobbyService getLobbyService() {
        return holder.getLobbyService();
    }

    @Override
    public FriendsService getFriendsService() {
        return holder.getFriendsService();
    }

    @Override
    public RewardService getRewardService() {
        return holder.getRewardService();
    }

    public static IService getService(IService service) {
        Plugin servicePlugin = Bukkit.getPluginManager().getPlugin("Services");
        if (servicePlugin != null && service == null) {
            return (MainService) servicePlugin;
        }

        return service;
    }
}
