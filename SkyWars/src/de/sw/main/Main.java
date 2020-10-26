package de.sw.main;

import de.sw.commands.*;
import de.sw.gameManager.GameState_Manager;
import de.sw.gameManager.Game_State;
import de.sw.listener.KitListener;
import de.sw.listener.PlayerConnectionEvent;
import de.sw.listener.ProtectionListener;
import de.sw.manager.InventoryManager;
import de.sw.manager.NMS;
import de.sw.manager.SBManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static Main instance;
    public static LuckPerms luckPerms;
    private NMS nmsHandler;

    public static String prefix = "§bSkyWars §8❘ §7";
    public static String noPerms = prefix + "§cDazu hast du keine Rechte!";
    public ArrayList<Player> players;
    private File file = new File("plugins/SkyWars", "Config.yml");
    public YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    public int playersInTeam;
    public int maxPlayers = yamlConfiguration.getInt("maxPlayers");
    public static InventoryManager inventoryManager = new InventoryManager();
    private GameState_Manager gameStateManager;
    private Listener kitListener;

    public SBManager sbManager = new SBManager();
    public static List<Player> build = new ArrayList<>();

    private int maxDoubleChest;
    public int getMaxDoubleChest() {
        return this.maxDoubleChest;
    }

    public void onEnable() {
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        this.instance = this;
        init();
    }

    public void init() {

        gameStateManager = new GameState_Manager(this);
        gameStateManager.setGameState(Game_State.ONLINE);
        players = new ArrayList<>();

        registerEvents();
        loadConfig();
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(prefix + "§eDas Plugin wurde erfolgreich gestartet!!!!");
    }

    public void registerCommands() {
        new Command_setup("setup");
        getCommand("leave").setExecutor((CommandExecutor)new Command_leave(this));
        getCommand("start").setExecutor((CommandExecutor) new Command_start());

    }

    public void registerEvents() {
        kitListener = new KitListener();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new PlayerConnectionEvent(this, this.luckPerms), this);
        pluginManager.registerEvents((Listener) new KitListener(), this);
        pluginManager.registerEvents((Listener) new ProtectionListener(), this);

    }

    public void loadConfig() {
        if(file.exists()) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§eDie Config wurde aktuallisiert!");
            return;
        }
        if(!file.exists()) {
            List<String> values = yamlConfiguration.getStringList("ChestItems");
            values.add("264:0, 1, 1");
            values.add("263:0, 1, 1");
            values.add("262:0, 1, 1");
            values.add("261:0, 1, 1");
            values.add("260:0, 1, 1");
            values.add("265:0, 1, 1");
            values.add("266:0, 1, 1");

            yamlConfiguration.set("ChestItems", values);
        }
        try {
            yamlConfiguration.save(file);
            Bukkit.getConsoleSender().sendMessage(prefix + "§eEs wurde eine Config erstellt!");
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public KitListener getKitListener() {
        return (KitListener) kitListener;
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

    public static NMS getNMS() {
        return instance.nmsHandler;
    }

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }
}
