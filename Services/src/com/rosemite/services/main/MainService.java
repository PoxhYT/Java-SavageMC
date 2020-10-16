package com.rosemite.services.main;

import com.rosemite.services.listener.PlayerJoinEvent;
import com.rosemite.services.services.coin.CoinService;
import com.rosemite.services.services.http.Http;
import com.rosemite.services.config.Config;
import com.rosemite.services.helper.Log;
import com.rosemite.services.services.player.PlayerService;
import com.rosemite.services.services.skywars.SkywarsService;
import com.rosemite.services.services.souptraining.SoupTrainingService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MainService extends JavaPlugin {
    public static String prefix = "§8[Service] §7";

    private ServiceHolder holder;
    private Config config;

    public void onEnable() {
        // Initialize Config data
        config = new Config(this);

        // Credentials
        String key = config.getConfiguration("backend.key").toString();
        String url = config.getConfiguration("backend.url").toString();

        // Initialize Services
        holder = new ServiceHolder(new Http(key, url));

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
