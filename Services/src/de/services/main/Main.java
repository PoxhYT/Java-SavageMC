package de.services.main;

import de.services.filemanager.FileManager;
import de.services.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    public static String prefix = "§8[MySQL] §7";
    public static Main instance;

    public MySQL mySQL;

    private FileManager manager;
    private final String path = "plugins/Services.json";

    public void onEnable() {
        instance = this;
        manager = new FileManager();

//        try {
//            getData();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // Connect to Database
        mySQL = new MySQL("localhost", "savagemc", "root", "somePassword", 3306);
        try {
            mySQL.connect();
            log("§eDas Plugin wurde erfolgreich gestartet!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {
        if (mySQL != null)
            mySQL.disable();
    }

//    private void getData() throws InterruptedException {
//        System.out.println(manager.loadData(path));
//    }

    private void log(Object message) { Bukkit.getConsoleSender().sendMessage(prefix + message); }

    public Main getInstance() { return instance; }
}
