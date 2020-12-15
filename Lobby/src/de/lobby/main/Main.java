package de.lobby.main;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.main.MainService;
import de.lobby.commands.*;
import de.lobby.listener.ChestLotteryListener;
import de.lobby.listener.PlayerInteractListener;
import de.lobby.listener.PlayerJoinListener;
import de.lobby.listener.ProtectionListener;
import de.lobby.manager.InventoryManager;
import de.lobby.manager.SBManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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
    public MongoDatabase mongoDatabase;

    //Objects
    public InventoryManager inventoryManager = new InventoryManager(mongoDatabase, services);
    public SBManager scoreboardAPI = new SBManager();

    //Lists
    public static List<Player> build = new ArrayList<>();
    public static List<Player> onlinePlayers = new ArrayList<>();

    //Booleans
    public static Boolean villagerSet = false;

    @Override
    public void onEnable() {
        init();
    }

    public void init() {

        if(!villagerSet) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§cDer Reward-Villager wurde noch nicht gesetzt. Bitte setzte Ihn bevor du ");
        }

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
        manager.registerEvents(new ProtectionListener(), this);
    }

    private void registerCommands() {
        getCommand("setup").setExecutor((CommandExecutor) new Command_setup());
        getCommand("tickets").setExecutor(new Command_tickets(mongoDatabase, services));
        getCommand("coins").setExecutor(new Command_coins(mongoDatabase, services));
        getCommand("build").setExecutor(new Command_build());
    }


    public static void scoreCD() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin)Main.getInstance(), new Runnable() {

            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    SBManager.updateScoreboard(all);
                }
            }
        }, 0, 1);
    }

    public static Main getInstance() {
        return instance;
    }
}
