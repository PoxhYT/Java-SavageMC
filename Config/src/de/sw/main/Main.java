package de.sw.main;

import de.sw.commands.Command_Admin;
import de.sw.commands.Command_Setup;
import de.sw.commands.Command_setsize;
import de.sw.commands.Command_start;
import de.sw.countdowns.ConfigCountdown;
import de.sw.countdowns.LobbyCountdown;
import de.sw.gamestate.GameState;
import de.sw.kit.Kit;
import de.sw.listener.InteractListener;
import de.sw.listener.PlayerJoinListener;
import de.sw.manager.InventoryManager;
import de.sw.manager.SBManager;
import de.sw.manager.Teams;
import de.sw.manager.User;
import de.sw.mysql.MySQL;
import de.sw.utils.DeathListener;
import de.sw.utils.ServerConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    public static Main instance;

    public int teams;

    public int playerInTeams;

    public String allKitsPermission;

    public List<Teams> teamList;


    public static GameState state;

    public static LobbyCountdown lobbyCountdown = new LobbyCountdown();

    public static ConfigCountdown configCountdown = new ConfigCountdown();

    public static ArrayList<Player> builder = new ArrayList<>();

    public static String prefix = "§bSkyWars §8❘ §7";

    public static InventoryManager inventoryManager = new InventoryManager();

    public List<Kit> kits;

    public static File file = new File("plugins/Config", "config.yml");

    public static YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    public static ServerConfig serverConfig = new ServerConfig(instance);


    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {

    }

    public void init() {
        registerEvents();
        instance = this;
        registerCommands();
        state = GameState.LOBBY;
        new ServerConfig(this);
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

    public void registerCommands() {
        new Command_Setup("setup");
        new Command_Admin("admin");
        new Command_start("start");
        new Command_setsize("setsize");
    }

    public void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new PlayerJoinListener(), this);
        pluginManager.registerEvents((Listener) new InteractListener(), this);
        pluginManager.registerEvents((Listener) new DeathListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public static LobbyCountdown getLobbyCountdown() {
        return lobbyCountdown;
    }
}
