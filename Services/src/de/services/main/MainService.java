package de.services.main;

import de.services.config.Config;
import de.services.helper.Log;
import de.services.mysql.MySQL;
import de.services.systems.PointSystem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public class MainService extends JavaPlugin {
    public static String prefix = "§8[MySQL] §7";

    private PointSystem pointSystem;

    private MySQL mySQL;

    private Config config;
    private String host, database, username, password;
    private int port;

    public void onEnable() {
        // Initialize Config data
        initializeConfig();

        // Init and Create Connection to MySQL
        initializeMySql();

        // Todo: Create SoupPointSystem (with ranking and stuff)
        pointSystem = new PointSystem(mySQL);
    }

    public void onDisable() {
        if (mySQL != null)
            mySQL.disable();
    }

    private void initializeConfig() {
        config = new Config(this);
        loadConfiguration();
    }

    private MySQL initializeMySql() {
        mySQL = new MySQL(host, database, username, password, port);

        // Connect to Database
        connectMySQL();

        return mySQL;
    }

    private void connectMySQL() {
        Connection connection = mySQL.connect();
        if (connection != null) {
            Log.log("§eMit MySQL verbunden!");
        }
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

        host = (String) config.getConfiguration(sql + "host");
        database = (String) config.getConfiguration(sql + "database");
        username = (String) config.getConfiguration(sql + "username");
        password = (String) config.getConfiguration(sql + "password");
        port = (int) config.getConfiguration(sql + "port");
    }

    public PointSystem getPointSystem() {
        return pointSystem;
    }
}