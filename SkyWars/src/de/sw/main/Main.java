package de.sw.main;

import com.rosemite.services.helper.Log;
import com.rosemite.services.models.skywars.PlayerSkywarsStats;
import de.gamestateapi.main.GameStateAPIManager;
import de.sw.commands.*;
import de.sw.countdown.*;
import de.sw.enums.Path;
import de.sw.listener.*;
import de.sw.manager.*;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Main extends JavaPlugin {
    //Strings
    public static String prefix = "§bSkyWars §8❘ §7";
    public static String noPerms = prefix + "§cDazu hast du keine Rechte!";

    //Instances
    public static Main instance;
    public static LuckPerms luckPerms;
    private Listener kitListener;
    private SkyWarsMapData data;
    public static ChestManager chestManager;

    //Objects
    public static InventoryManager inventoryManager = new InventoryManager();
    public SBManager sbManager = new SBManager();
    public static LobbyCountdown countdown = new LobbyCountdown();
    public static ProtectionCountdown protectionCountdown = new ProtectionCountdown();
    public static MoveCountdown moveCountdown = new MoveCountdown();
    public static RestartCountdown restartCountdown = new RestartCountdown();
    public static EndingCountdown endingCountdown = new EndingCountdown();
    public static Map<Player, KitManager> kitMap = new HashMap<>();

    //HashMap
    public static HashMap<UUID, PlayerSkywarsStats> stats = new HashMap<>();
    public static Map<UUID, String> teamManagerMap = new HashMap<>();

    //YamlConfigurations
    private File file = new File("plugins/SkyWars", "Config.yml");
    public YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    public static File fileSkyWars = new File("plugins/SkyWars", "MapData.yml");
    public static YamlConfiguration yamlConfigurationSkyWars = YamlConfiguration.loadConfiguration(fileSkyWars);
    //addItemLists
    public static File itemFile = new File("plugins/SkyWars", "Items.yml");
    public static FileConfiguration itemConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(itemFile);

    //ArrayLists
    public static ArrayList<Player> alivePlayers = new ArrayList<>();
    public static List<Player> build = new ArrayList<>();
    public static Map<String, Object> MapName1;
    public TeamManager[] teams;
    public static List<Map<String, Object>> maps = (List<Map<String, Object>>) yamlConfigurationSkyWars.getList("maps");
    public static ArrayList<Player> moveMap = new ArrayList<>();
    public static List<ItemStack> items = new ArrayList<>();
    public static List<Map<String, Object>> normal = (List<Map<String, Object>>) itemConfiguration.getList("normal");
    public static List<Map<String, Object>> center = (List<Map<String, Object>>) itemConfiguration.getList("center");


    public void onEnable() {
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        this.instance = this;
        init();
        Log.d(items.size());
    }

    public void init() {
        SkyWarsMapData map = chooseRandom();

        chestManager = new ChestManager(map.getLocationOfMiddlePoint(), map);

        loadFiles();
        GameStateAPIManager.setState(GameStateAPIManager.LOBBY);

        Log.d("ANZAHL DER MAPS:" + maps.size());

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
        load("config.yml");
    }

    public SkyWarsMapData chooseRandom() {
        List<Map<String, Object>> result = maps.stream().filter(map -> !(boolean) map.get(Path.StillUnderDevelopment.toString())).collect(Collectors.toList());

        loadFiles();
        Random random = new Random();
        Log.d(result.size());
        int mapsSize = random.nextInt(result.size());
        Map<String, Object> finalMap = result.get(1);
//        Map<String, Object> finalMap = result.get(mapsSize);
        Log.d(finalMap.get(Path.MapName.toString()));

        MapName1 = finalMap;

        Bukkit.getConsoleSender().sendMessage("§eThe current Map is: " + finalMap.get(Path.MapName.toString()));
        Bukkit.getConsoleSender().sendMessage("§eThe current Size is: " + finalMap.get(Path.GameSize.toString()));

        int teamCount = (int)finalMap.get(Path.MaxTeamCount.toString());
        Location[] locations = new Location[teamCount];
        List<Map<String, Object>> locs = (List<Map<String, Object>>) finalMap.get(Path.Locations.toString());

        for (int i = 0; i < teamCount; i++) {
            Map<String, Object> m = locs.get(i);

            locations[i] = new Location(Bukkit.getServer().getWorld((String) m.get("world")), (double)m.get("x"), (double)m.get("y"), (double) m.get("z"), (float) (double)m.get("pitch"), (float) (double) m.get("yaw") );
        }

        this.data = new SkyWarsMapData(
            (int) finalMap.get(Path.Id.toString()),
            (String) finalMap.get(Path.MapName.toString()),
            (String) finalMap.get(Path.GameSize.toString()),
            locations,
            (int)finalMap.get(Path.MaxTeamCount.toString()),
            (int)finalMap.get(Path.MaxPlayersInTeam.toString()),
            (boolean)finalMap.get(Path.StillUnderDevelopment.toString())
        );

        return this.data;
    }


    public void registerCommands() {
        getCommand("start").setExecutor((CommandExecutor) new Command_start());
        getCommand("build").setExecutor((CommandExecutor)new Command_build());
        getCommand("create").setExecutor(new Command_create(Main.getInstance()));
        getCommand("createBlock").setExecutor(new Command_createBlock());
        getCommand("location").setExecutor(new Command_location());
        getCommand("setup").setExecutor(new Command_setup());
        getCommand("addItem").setExecutor(new Command_addItem());
        getCommand("enchantAll").setExecutor(new Command_enchantAll());
    }

    public void registerEvents(SkyWarsMapData map) {
        kitListener = new KitListener();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new PlayerConnectionEvent(this, this.luckPerms), this);
        pluginManager.registerEvents((Listener) new KitListener(), this);
        pluginManager.registerEvents((Listener) new ProtectionListener(), this);
        pluginManager.registerEvents((Listener) new TeamListener(map, luckPerms), this);
        pluginManager.registerEvents(new ServerPingListener(), this);

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

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }

    public SkyWarsMapData getSkyWarsMapData() {
        return data;
    }

}
