package com.rosemite.services.main;

import com.rosemite.services.models.player.PlayerInfo;
import com.rosemite.services.services.http.Http;
import com.rosemite.services.config.Config;
import com.rosemite.services.helper.Log;
import com.rosemite.services.services.skywars.SkywarsServices;
import com.rosemite.services.services.souptraining.SoupTrainingService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class MainService extends JavaPlugin {
    public static String prefix = "§8[Service] §7";

    private ServiceHolder holder;

    private Map<String, PlayerInfo> players;
    private Config config;

    public void onEnable() {
        // Initialize Config data
        config = new Config(this);

        String key = config.getConfiguration("backend.key").toString();
        String url = config.getConfiguration("backend.url").toString();

        // Initialize Services
        holder = new ServiceHolder(new Http(key, url));

        Log.d("Loaded Services Successfully");
    }

    public PlayerInfo getPlayerInfo(String uuid) {
        if (players.get(uuid) == null) {
            // TODO: Try to fetch player. If found return else return null
        }

        return players.get(uuid);
    }

    public SoupTrainingService getPointSystem() {
        return holder.getSoupTrainingService();
    }

    public SkywarsServices getSkywarsServices() {
        return holder.getSkywarsServices();
    }

    public static MainService getService(MainService service) {
        Plugin servicePlugin = Bukkit.getPluginManager().getPlugin("Services");
        if (servicePlugin != null && service == null) {
            return (MainService) servicePlugin;
        }

        return service;
    }
}
