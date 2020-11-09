package de.cba.main;

import de.cba.commands.Command_inventory;
import de.cba.listener.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static String prefix = "§eCityBuild §8❘ §7";


    public void onEnable() {
        init();
    }


    public void onDisable() {

    }

    private void init() {
        registerCommands();
        registerEvents();
        Bukkit.getConsoleSender().sendMessage("§aERFOLGREICH GESTARTET!");
    }

    private void registerCommands() {
        getCommand("inv").setExecutor(new Command_inventory());
    }

    public void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new PlayerInteractListener(), this);
    }
}
