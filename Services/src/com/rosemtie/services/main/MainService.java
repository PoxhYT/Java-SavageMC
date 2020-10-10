package com.rosemtie.services.main;

import com.rosemtie.services.backend.http.Http;
import com.rosemtie.services.backend.http.HttpType;
import com.rosemtie.services.config.Config;
import com.rosemtie.services.helper.Log;
import com.rosemtie.services.systems.CoinSystem;
import com.rosemtie.services.systems.PointSystem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainService extends JavaPlugin {
    public static String prefix = "§8[Service] §7";

    private PointSystem pointSystem;
    private CoinSystem coinSystem;
    private Config config;

    private Http http;

    public void onEnable() {
        // Initialize Config data
        initializeConfig();

        http = new Http();

        try {

            String data = http.request("https://httpbin.org/get", HttpType.GET, new HashMap<String, Object>());
            http.toMap(data);
            Map<String, Object> a = http.toMap(data);
            Log.d(a.get("headers"));
//            Log.d(http.toString(a));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Todo: Create SoupPointSystem (with ranking and stuff)
        pointSystem = new PointSystem();
        coinSystem = new CoinSystem();

        Log.d("Loaded Services");
    }

    private void initializeConfig() {
        config = new Config(this);
        loadConfiguration();
    }

    public static MainService getService(MainService service) {
        Plugin servicePlugin = Bukkit.getPluginManager().getPlugin("Services");
        if (servicePlugin != null && service == null) {
            return (MainService) servicePlugin;
        }

        return service;
    }

    private void loadConfiguration() {
//       final String sql = "mysql.credentials.";
    }

    public PointSystem getPointSystem() {
        return pointSystem;
    }
}
