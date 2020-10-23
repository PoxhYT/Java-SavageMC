package de.sw.main;

import de.anweisung.premiumkickapi.PremiumKick;
import de.sw.commands.Command_leave;
import de.sw.commands.Command_setup;
import de.sw.commands.Command_start;
import de.sw.gameManager.GameState_Manager;
import de.sw.gameManager.Game_State;
import de.sw.listener.KitListener;
import de.sw.listener.PlayerConnectionEvent;
import de.sw.listener.ProtectionListener;
import de.sw.manager.InventoryManager;
import de.sw.manager.SBManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static Main instance;

    public static boolean ff = false;

    public static String prefix = "§bSkyWars §8❘ §7";

    public static String noPerms = prefix + "§cDazu hast du keine Rechte!";

    public ArrayList<Player> players;

    private File file = new File("plugins/SkyWars", "Config.yml");

    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public int playersInTeam;

    public int maxPlayers = yamlConfiguration.getInt("maxPlayers");

    public static InventoryManager inventoryManager = new InventoryManager();

    public String allKitsPerm;

    private GameState_Manager gameStateManager;

    public SBManager sbManager = new SBManager();

    public static List<Player> build = new ArrayList<>();

    public void onEnable() {
        init();
    }

    public void init() {
        gameStateManager = new GameState_Manager(this);
        gameStateManager.setGameState(Game_State.ONLINE);
        players = new ArrayList<>();

        registerEvents();
        instance = this;
        loadConfig();
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(prefix + "§eDas Plugin wurde erfolgreich gestartet!!!!");
        PremiumKick.allowPremiumKick();
    }

    public void registerCommands() {
        new Command_setup("setup");
        getCommand("leave").setExecutor((CommandExecutor)new Command_leave(this));
        getCommand("start").setExecutor((CommandExecutor) new Command_start());

    }

    public void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new PlayerConnectionEvent(), this);
        pluginManager.registerEvents((Listener) new KitListener(), this);
        pluginManager.registerEvents((Listener) new ProtectionListener(), this);
    }

    public void loadConfig() {
        if(file.exists()) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§eDie Config wurde aktuallisiert!");
            return;
        }
        if(!file.exists()) {
            yamlConfiguration.set("minplayers", 1);
            yamlConfiguration.set("maxPlayers", 3);
            yamlConfiguration.set("teams", 8);
            yamlConfiguration.set("playersInTeam", 1);
            yamlConfiguration.set("gameSize", "8x1");
            yamlConfiguration.set("MapName", "Forest");
        }
        try {
            yamlConfiguration.save(file);
            Bukkit.getConsoleSender().sendMessage(prefix + "§eEs wurde eine Config erstellt!");
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public GameState_Manager getGameStateManager() {
        return gameStateManager;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
