package src.de.cba.main;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.main.MainService;
import com.rosemite.services.services.coin.CoinService;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import src.de.cba.commands.Command_InventorySee;
import src.de.cba.commands.Command_Pay;
import src.de.cba.commands.Command_coins;
import src.de.cba.commands.Command_inventory;
import src.de.cba.listener.BlockBreakListener;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    //Strings
    public static String prefix = "§eCityBuild §8❘ §7";
    public static String onlyPlayers = prefix + "§cNur Spieler können dieses Befehl ausführen!";

    //HashMap
    public static HashMap<UUID, CoinService> coinService1 = new HashMap<>();


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
        getCommand("pay").setExecutor(new Command_Pay());
    }


    public void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new BlockBreakListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }
}
