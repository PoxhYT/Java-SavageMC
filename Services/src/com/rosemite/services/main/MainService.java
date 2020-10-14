package com.rosemite.services.main;

import com.github.jlabsys.ObjectMapper;
import com.google.gson.Gson;
import com.rosemite.services.backend.http.Http;
import com.rosemite.services.backend.http.HttpType;
import com.rosemite.services.config.Config;
import com.rosemite.services.helper.Log;
import com.rosemite.services.models.HttpResponse;
import com.rosemite.services.models.SoupScoreModel;
import com.rosemite.services.services.skywars.SkywarsServices;
import com.rosemite.services.systems.CoinSystem;
import com.rosemite.services.systems.PlayerSystem;
import com.rosemite.services.systems.PointSystem;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainService extends JavaPlugin {
    public static String prefix = "§8[Service] §7";

    private PlayerSystem playerSystem;
    private PointSystem pointSystem;
    private CoinSystem coinSystem;

    private SkywarsServices skywarsServices;

    private Config config;

    private Http http;

    public void onEnable() {
        // Initialize Config data
        config = new Config(this);
        String key = config.getConfiguration("backend.key").toString();
        String url = config.getConfiguration("backend.url").toString();

        // Initialize Http Client
        http = new Http(key, url);

        // Initialize Systems
        playerSystem = new PlayerSystem(http);
        pointSystem = new PointSystem(http);
        coinSystem = new CoinSystem(http);

        // Initialize Services
        skywarsServices = new SkywarsServices(http);

        Log.d("Loaded Services Successfully");
    }

    public PointSystem getPointSystem() {
        return pointSystem;
    }

    public SkywarsServices getSkywarsServices() {
        return skywarsServices;
    }

    public static MainService getService(MainService service) {
        Plugin servicePlugin = Bukkit.getPluginManager().getPlugin("Services");
        if (servicePlugin != null && service == null) {
            return (MainService) servicePlugin;
        }

        return service;
    }
}
