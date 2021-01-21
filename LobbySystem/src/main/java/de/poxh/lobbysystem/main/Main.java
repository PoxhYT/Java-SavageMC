package de.poxh.lobby.main;

import com.mongodb.client.MongoDatabase;
import com.rosemite.models.service.common.IService;
import com.rosemite.services.main.MainService;
import de.poxh.lobby.chestOpener.ChestOpener;
import de.poxh.lobby.commands.Command_CreateEntity;
import de.poxh.lobby.commands.Command_build;
import de.poxh.lobby.commands.Command_setSpawn;
import de.poxh.lobby.listener.*;
import de.poxh.lobby.manager.EntityManager;
import de.poxh.lobby.manager.SBManager;
import de.poxh.lobby.manager.ScheduleManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    //Strings
    public static String prefix = "§eSavageMC §7❘ §7» ";
    public static String noPerms = prefix + "§cDazu hast du nicht genügend Rechte!";

    //Arrays
    public static ArrayList<Player> build = new ArrayList<>();


    public static Main instance;
    public static LuckPerms luckPerms;
    private static LuckPerms luckPermsAPI;

    //Service
    public static IService service = MainService.getService(null);
    private MongoDatabase mongoDatabase;

    public static EntityManager entityManager = new EntityManager();
    public static SBManager sbManager = new SBManager();
    public ScheduleManager scheduleManager = new ScheduleManager(this);


    private void init() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        instance = this;
//        scheduleManager.scoreCD();
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        registerEvents();
        registerCommands();
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {

    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ChestLottery(service, mongoDatabase, this), this);
        pluginManager.registerEvents(new PlayerJoinListener(luckPerms), this);
        pluginManager.registerEvents(new EntityManager(), this);
        pluginManager.registerEvents(new RewardListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new ProtectionListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new FriendListener(service), this);
    }

    private void registerCommands() {
        getCommand("create").setExecutor(new Command_CreateEntity());
        getCommand("setspawn").setExecutor(new Command_setSpawn());
        getCommand("build").setExecutor(new Command_build());
        getCommand("gamble").setExecutor(new ChestOpener());
    }

    public static Main getInstance() {
        return instance;
    }

    public static LuckPerms getLuckPermsAPI() {
        return luckPermsAPI;
    }
}
