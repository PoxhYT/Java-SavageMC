package com.rosemite.services.main;

import com.github.jlabsys.ObjectMapper;
import com.rosemite.services.backend.http.Http;
import com.rosemite.services.backend.http.HttpType;
import com.rosemite.services.config.Config;
import com.rosemite.services.helper.Log;
import com.rosemite.services.models.HttpResponse;
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
        initializeConfig();

        // Initialize Http Client
        http = new Http();

        // Initialize Systems
        playerSystem = new PlayerSystem(http);
        pointSystem = new PointSystem(http);
        coinSystem = new CoinSystem(http);

        Map<String, Object> body = new HashMap<>();
        body.put("Age", 21);
        body.put("test", true);

        Map<String, String> header = new HashMap<>();
        header.put("path", "SoupTraining/uuid 1");
        header.put("key", ""); // Todo: Key here

//        try {
//            HttpResponse data = http.request("http://localhost:3000/", HttpType.GET, body, header);
//
//            Map<String, Object> map = data.getAsMap();
//
//            Log.d(data.statusCode);
//            Log.d(data.content);
//
//            ObjectMapper mapper = new ObjectMapper();
//
//            //JSON URL to Java object
////            Http obj = mapper.isGetter(data.content, Http.class);
//
////            Log.d(map.get("easy"));
////            Log.d(map.get("uuid"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Log.d("Loaded Services Successfully");
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
