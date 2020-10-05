package de.services.main;

import de.services.config.Config;
import de.services.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    public static String prefix = "§8[MySQL] §7";

    public MySQL mySQL;

    private Config config;

    private String host, database, username, password;

    private int port;

    public void onEnable() {
        config = new Config(this);

        loadConfiguration();
        mySQL = new MySQL(host, database, username, password, port);

        // Connect to Database
        connectMySQL();
    }

    public void onDisable() {
        if (mySQL != null)
            mySQL.disable();
    }

    private void connectMySQL() {
        try {
            mySQL.connect();
            log("§eMit MySQL verbunden!");
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void loadConfiguration() {
        final String sql = "mysql.credentials.";

        host = (String) config.getConfiguration(sql+"host");
        database = (String) config.getConfiguration(sql+"database");
        username = (String) config.getConfiguration(sql+"username");
        password = (String) config.getConfiguration(sql+"password");
        port = (int) config.getConfiguration(sql+"port");
    }

    private void log(Object message) { Bukkit.getConsoleSender().sendMessage(prefix + message); }
}
