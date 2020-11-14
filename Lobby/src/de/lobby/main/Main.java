package de.lobby.main;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.main.MainService;
import de.lobby.api.ScoreboardAPI;
import de.lobby.commands.Command_coins;
import de.lobby.commands.Command_setup;
import de.lobby.commands.Command_tickets;
import de.lobby.listener.ChestLotteryListener;
import de.lobby.listener.PlayerInteractListener;
import de.lobby.listener.PlayerJoinListener;
import de.lobby.manager.InventoryManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    //Strings
    public static String prefix = "§eSavageMC §7❘ §7";
    public static String noPerms = prefix + "§cDazu hast du nicht genügend Rechte!";

    //Instances
    public static Main instance;
    public static LuckPerms luckPerms;
    private MainService services;
    private MongoDatabase mongoDatabase;

    //Objects
    public static InventoryManager inventoryManager = new InventoryManager();

    //Lists
    public static List<Player> build = new ArrayList<>();

    @Override
    public void onEnable() {
        init();
    }

    public void init() {

        //Instances
        this.mongoDatabase = mongoDatabase;
        this.services = MainService.getService(services);
        this.instance = this;

        //Objects
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);

        //Strings
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "§eDas Plugin wurde erfolgreich gestartet!");

        //register
        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "§cDas Plugin wurde erfolgreich beendet!");
    }

    private void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents((Listener) new PlayerJoinListener(this, this.luckPerms), this);
        manager.registerEvents((Listener) new PlayerInteractListener(), this);
        manager.registerEvents(new ChestLotteryListener(services, mongoDatabase), this);
    }

    private void registerCommands() {
        getCommand("setup").setExecutor((CommandExecutor) new Command_setup());
        getCommand("tickets").setExecutor(new Command_tickets(mongoDatabase, services));
        getCommand("coins").setExecutor(new Command_coins(mongoDatabase, services));
    }


    public static void scoreCD() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin)Main.getInstance(), new Runnable() {

            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    ScoreboardAPI.updateScoreboard(all);
                }
            }
        }, 0, 1);
    }

    public static Main getInstance() {
        return instance;
    }
}
