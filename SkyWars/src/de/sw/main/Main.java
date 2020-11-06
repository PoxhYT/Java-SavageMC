package de.sw.main;

import com.rosemite.services.helper.Log;
import com.rosemite.services.models.skywars.PlayerSkywarsStats;
import de.sw.commands.*;
import de.sw.enums.Path;
import de.sw.gameManager.GameState_Manager;
import de.sw.gameManager.Game_State;
import de.sw.listener.*;
import de.sw.manager.*;
import io.netty.channel.ChannelPipeline;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class Main extends JavaPlugin {

    public static Main instance;
    public static LuckPerms luckPerms;
    public static String prefix = "§bSkyWars §8❘ §7";
    public  static boolean teams = false;
    public static String noPerms = prefix + "§cDazu hast du keine Rechte!";
    public ArrayList<Player> players;
    private File file = new File("plugins/SkyWars", "Config.yml");
    public YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    public static InventoryManager inventoryManager = new InventoryManager();
    private GameState_Manager gameStateManager;
    private Listener kitListener;

    public static HashMap<UUID, PlayerSkywarsStats> roundKills = new HashMap<>();
    public static ArrayList<Player> alivePlayers = new ArrayList<>();

    @Getter
    public SBManager sbManager = new SBManager();
    public static List<Player> build = new ArrayList<>();
    public static Map<String, Object> MapName1;

    private int maxDoubleChest;


    private static File fileSkywars = new File("plugins/SkyWars", "MapData.yml");
    private static YamlConfiguration yamlConfigurationSkyWars = YamlConfiguration.loadConfiguration(fileSkywars);

    public int getMaxDoubleChest() {
        return this.maxDoubleChest;
    }

    public void onEnable() {
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        this.instance = this;
        init();

    }

    public void init() {
        SkyWarsMapData map = chooseRandom();
        loadFiles();
        teams = true;

        gameStateManager = new GameState_Manager(this);
        gameStateManager.setGameState(Game_State.ONLINE);
        players = new ArrayList<>();

        registerEvents(map);
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(prefix + "§eDas Plugin wurde erfolgreich gestartet!!!!");
    }

    public void load(String fileName) {
        File files = new File(Main.instance.getDataFolder(), fileName);
        if (!files.exists())
            Main.instance.saveResource(fileName, false);
            Bukkit.getConsoleSender().sendMessage(Main.prefix + "§eDie Yml-Datei : " + fileName + " §ewurde erfolgreich erstellt!");
        if (files.exists()) {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(files);
            Bukkit.getConsoleSender().sendMessage(Main.prefix + "§eAlle Yml's wurden aktuallisiert!");
        }
    }

    public void loadFiles() {
        load("MapData.yml");
    }

    public static SkyWarsMapData chooseRandom() {
        Random random = new Random();
        List<Map<String, Object>> maps = (List<Map<String, Object>>) yamlConfigurationSkyWars.getList("maps");
        int mapsSize = random.nextInt(maps.size());
         Map<String, Object> finalMap = maps.get(mapsSize);
        MapName1 = finalMap;

        Bukkit.getConsoleSender().sendMessage("§eThe current Map is: " + finalMap.get(Path.MapName.toString()));
        Bukkit.getConsoleSender().sendMessage("§eThe current Size is: " + finalMap.get(Path.GameSize.toString()));
        Bukkit.getConsoleSender().sendMessage("§eLOL: " + MapName1.get(Path.MapName.toString()));
        String TEST = (String) finalMap.get(Path.GameSize.toString());
        try {
            yamlConfigurationSkyWars.set("currentMap", TEST);
            yamlConfigurationSkyWars.save(fileSkywars);
        }catch (IOException e){}
        SkyWarsMapData data = new SkyWarsMapData(
            (String) finalMap.get(Path.MapName.toString()),
            (String) finalMap.get(Path.GameSize.toString()),
            (int)finalMap.get(Path.MaxTeamCount.toString()),
            (int)finalMap.get(Path.MaxPlayersInTeam.toString())

        );
        return data;
    }


    public void registerCommands() {
        getCommand("start").setExecutor((CommandExecutor) new Command_start());
        getCommand("build").setExecutor((CommandExecutor)new Command_build());

    }

    public void registerEvents(SkyWarsMapData map) {
        kitListener = new KitListener();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new PlayerConnectionEvent(this, this.luckPerms), this);
        pluginManager.registerEvents((Listener) new KitListener(), this);
        pluginManager.registerEvents((Listener) new PlayerTeleportListener(), this);
        pluginManager.registerEvents((Listener) new ProtectionListener(), this);
        pluginManager.registerEvents((Listener) new TeamListener(map), this);

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

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }
}
