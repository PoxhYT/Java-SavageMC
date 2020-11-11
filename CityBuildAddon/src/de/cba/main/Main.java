package de.cba.main;

import de.cba.commands.Command_InventorySee;
import de.cba.commands.Command_TPA;
import de.cba.commands.Command_TPAccept;
import de.cba.commands.Command_inventory;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    //Strings
    public static String prefix = "§eCityBuild §8❘ §7";


    @Getter
    private static Main instance;



    public void onEnable() {
        init();
    }


    public void onDisable() {

    }

    private void init() {
        registerCommands();
        registerEvents();
        instance = this;
        Bukkit.getConsoleSender().sendMessage("§aERFOLGREICH GESTARTET!");
    }

    private void registerCommands() {
        getCommand("inv").setExecutor(new Command_inventory());
        getCommand("invsee").setExecutor(new Command_InventorySee());
        getCommand("tpa").setExecutor(new Command_TPA());
        getCommand("tpaccept").setExecutor(new Command_TPAccept());
        getCommand()
    }


    public void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        //pluginManager.registerEvents((Listener) new PlayerInteractListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }
}
