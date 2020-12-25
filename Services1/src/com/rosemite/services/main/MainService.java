package com.rosemite.services.main;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.rosemite.services.listener.PlayerJoinEvent;
import com.rosemite.services.service.report.ReportService;
import com.rosemite.services.services.coin.CoinService;
import com.rosemite.services.helper.Log;
import com.rosemite.services.services.lobby.LobbyService;
import com.rosemite.services.services.player.PlayerService;
import com.rosemite.services.services.skywars.SkywarsService;
import com.rosemite.services.services.souptraining.SoupTrainingService;
import com.rosemite.services.services.ticket.TicketService;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainService extends JavaPlugin{
    private ServiceHolder holder;

    public static String prefix = "§8[§eServices§8] §7";

    public void onEnable() {


        // Initialize Config data


//            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
//            configuration.set("backend.url" ,"mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false");
//            configuration.save(file);
//
//            YamlConfiguration.loadConfiguration(file);
//
//            // Get Credentials & Connect to Mongodb
//            String connectionString = configuration.getString("backend.url");

        MongoClient mongoClient = MongoClients.create("mongodb+srv://admin:aynea07aOkiaactF@cluster0.u4x7t.mongodb.net/SavageMC?retryWrites=true&w=majority");
        MongoDatabase db = mongoClient.getDatabase("SavageMC");
        Bukkit.getConsoleSender().sendMessage(prefix + "§eLoaded Services Succsessfully!");

        // Initialize Services
        holder = new ServiceHolder(db,this);

        // Register Events
        registerEvents();


    }

    private void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents((Listener) new PlayerJoinEvent(holder.getPlayerService(), holder.getSkywarsService()),this);
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

    public TicketService getTicketService() {
        return holder.getTicketService();
    }

    public LobbyService getLobbyService() {
        return holder.getLobbyService();
    }

    public static MainService getService(MainService service) {
        JavaPlugin servicePlugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("Services");
        if (servicePlugin != null && service == null) {
            return (MainService) servicePlugin;
        }

        return service;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args, Location location) {
        return null;
    }
}
