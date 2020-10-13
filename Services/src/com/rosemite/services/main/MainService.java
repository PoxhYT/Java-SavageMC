package com.rosemite.services.main;

import com.github.jlabsys.ObjectMapper;
import com.google.gson.Gson;
import com.rosemite.services.backend.http.Http;
import com.rosemite.services.backend.http.HttpType;
import com.rosemite.services.config.Config;
import com.rosemite.services.helper.Log;
import com.rosemite.services.models.HttpResponse;
import com.rosemite.services.models.SoupScoreModel;
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

    private Config config;

    private Http http;

    public void onEnable() {
        // Initialize Config data
        config = new Config(this);
        String key = config.getConfiguration("backend.key").toString();
        String url = "http://localhost:3000/";
//        String url = config.getConfiguration("backend.url").toString();

        // Initialize Http Client
        http = new Http(key, url);

        // Initialize Systems
        playerSystem = new PlayerSystem(http);
        pointSystem = new PointSystem(http);
        coinSystem = new CoinSystem(http);

        Map<String, Object> body = new HashMap<>();
        body.put("Age", 21);
        body.put("test", true);

        Map<String, String> header = new HashMap<>();
        header.put("path", "SoupTraining/uuid 1");
        header.put("key", key);

//        try {
//            HttpResponse data = http.request("http://localhost:3000/", HttpType.GET, body, header);
//
//            Log.d(data.statusCode);
//            Log.d(data.content);
//
//            String json = new Gson().toJson(data.getAsMap().get("data"));
//            SoupScoreModel score = new Gson().fromJson(json, SoupScoreModel.class);
//
//            Log.d(score.HARD);
//            Log.d(score.LEGEND);
//            Log.d(score.UUID);
//            Log.d(score.SLOW);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Log.d("Loaded Services Successfully");
    }

    public static MainService getService(MainService service) {
        Plugin servicePlugin = Bukkit.getPluginManager().getPlugin("Services");
        if (servicePlugin != null && service == null) {
            return (MainService) servicePlugin;
        }

        return service;
    }

    public PointSystem getPointSystem() {
        return pointSystem;
    }
}
