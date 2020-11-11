package de.cba.main;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.main.MainService;
import com.rosemite.services.models.skywars.PlayerSkywarsStats;
import com.rosemite.services.services.coin.CoinService;
import de.cba.commands.Command_InventorySee;
import de.cba.commands.Command_coins;
import de.cba.commands.Command_inventory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    //Strings
    public static String prefix = "§eCityBuild §8❘ §7";
    public static String onlyPlayers = prefix + "§cNur Spieler können dieses Befehl ausführen!";

    //HashMap
    public static HashMap<UUID, CoinService> coinService = new HashMap<>();


    private static Main instance;
    private MainService services;
    private MongoDatabase mongoDatabase;



    public void onEnable() {
        init();
    }


    public void onDisable() {

    }

    private void init() {
        registerCommands();
        registerEvents();
        instance = this;
        services = MainService.getService(services);
        this.mongoDatabase = mongoDatabase;
        Bukkit.getConsoleSender().sendMessage("§aERFOLGREICH GESTARTET!");
    }

    private void registerCommands() {
        getCommand("inv").setExecutor(new Command_inventory());
        getCommand("invsee").setExecutor(new Command_InventorySee());
        getCommand("coins").setExecutor(new Command_coins(services, mongoDatabase));
    }


    public void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        //pluginManager.registerEvents((Listener) new PlayerInteractListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }
}
