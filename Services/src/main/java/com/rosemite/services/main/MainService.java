package com.rosemite.services.main;

import com.rosemite.services.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.rosemite.services.helper.Log;
import com.rosemite.services.systems.PointSystem;

public class MainService extends JavaPlugin {
    public static String prefix = "§8[Service] §7";

    private PointSystem pointSystem;
    private Config config;

    public enum SpeedType {
        NOOB, SLOW, NORMAL, HARD, LEGEND;
    }

    public void onEnable() {
        Log.log("Loaded Services");
        // Initialize Config data
//        initializeConfig();
//
//        // Todo: Create SoupPointSystem (with ranking and stuff)
        pointSystem = new PointSystem();
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
        final String sql = "mysql.credentials.";

//        host = (String) config.getConfiguration(sql + "host");
//        database = (String) config.getConfiguration(sql + "database");
//        username = (String) config.getConfiguration(sql + "username");
//        password = (String) config.getConfiguration(sql + "password");
//        port = (int) config.getConfiguration(sql + "port");
    }

    public PointSystem getPointSystem() {
        return pointSystem;
    }
}
