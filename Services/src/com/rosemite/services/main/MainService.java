package com.rosemite.services.main;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.services.listener.PlayerJoinEvent;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.service.report.ReportService;
import com.rosemite.services.services.coin.CoinService;
import com.rosemite.services.config.Config;
import com.rosemite.services.helper.Log;
import com.rosemite.services.services.player.PlayerService;
import com.rosemite.services.services.skywars.SkywarsService;
import com.rosemite.services.services.souptraining.SoupTrainingService;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MainService extends JavaPlugin {
    private ServiceHolder holder;

    public void onEnable() {
        // Initialize Config data
        Config config = new Config(this);

        // Get Credentials & Connect to Mongodb
        String connectionString = config.getConfiguration("backend.url").toString();

        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase db = mongoClient.getDatabase("savagemc");

        // Initialize Services
        holder = new ServiceHolder(db,this);

        // Register Events
        registerEvents();

        Log.d("Loaded Services Successfully");
    }

    private void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerJoinEvent(
                holder.getPlayerService(),
                holder.getSkywarsService()
        ), this);
    }

    public SoupTrainingService getSoupTrainingService() {
        return holder.getSoupTrainingService();
    }

    public SkywarsService getSkywarsService() {
        return holder.getSkywarsService();
    }

    public CoinService getCoinService() {
        return holder.getCoinService();
    }

    public ReportService getReportService() {
        return holder.getReportService();
    }

    public PlayerService getPlayerService() {
        return holder.getPlayerService();
    }

    public static MainService getService(MainService service) {
        Plugin servicePlugin = Bukkit.getPluginManager().getPlugin("Services");
        if (servicePlugin != null && service == null) {
            return (MainService) servicePlugin;
        }

        return service;
    }
}
