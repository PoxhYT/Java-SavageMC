package de.sw.main;

import com.rosemite.services.models.skywars.PlayerSkywarsStats;
import de.sw.commands.*;
import de.sw.countdown.LobbyCountdown;
import de.sw.countdown.MoveCountdown;
import de.sw.countdown.ProtectionCountdown;
import de.sw.enums.Path;
import de.sw.gameManager.GameStateManager;
import de.sw.listener.*;
import de.sw.manager.*;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class Main extends JavaPlugin {

    //Strings
    public static String prefix = "§bSkyWars §8❘ §7";
    public static String noPerms = prefix + "§cDazu hast du keine Rechte!";


    //Instances
    public static Main instance;
    public static LuckPerms luckPerms;
    private Listener kitListener;
    private SkyWarsMapData data;

    //Objects
    public static InventoryManager inventoryManager = new InventoryManager();
    public SBManager sbManager = new SBManager();
    public LobbyCountdown countdown = new LobbyCountdown();
    public static ProtectionCountdown protectionCountdown = new ProtectionCountdown();
    public static MoveCountdown moveCountdown = new MoveCountdown();



    //HashMap
    public static HashMap<UUID, PlayerSkywarsStats> stats = new HashMap<>();


    //YamlConfigurations
    private File file = new File("plugins/SkyWars", "Config.yml");
    public YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    public static File fileSkyWars = new File("plugins/SkyWars", "MapData.yml");
    public static YamlConfiguration yamlConfigurationSkyWars = YamlConfiguration.loadConfiguration(fileSkyWars);


    //ArrayLists
    public static ArrayList<Player> alivePlayers = new ArrayList<>();
    public static List<Player> build = new ArrayList<>();
    public static Map<String, Object> MapName1;
    public TeamManager[] teams;
    public static List<Map<String, Object>> maps = (List<Map<String, Object>>) yamlConfigurationSkyWars.getList("maps");
    public static ArrayList<Player> moveMap = new ArrayList<>();



    public void onEnable() {
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        this.instance = this;
        init();
    }

    public void init() {
        SkyWarsMapData map = chooseRandom();
        loadFiles();
        GameStateManager.setState(GameStateManager.LOBBY);

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
        Random random = new Random();
        int mapsSize = random.nextInt(maps.size());
        Map<String, Object> finalMap = maps.get(mapsSize);


        MapName1 = finalMap;

        Bukkit.getConsoleSender().sendMessage("§eThe current Map is: " + finalMap.get(Path.MapName.toString()));
        Bukkit.getConsoleSender().sendMessage("§eThe current Size is: " + finalMap.get(Path.GameSize.toString()));

        // Calculate Locations
        //ArrayList<Map<String, Object>> ls = (ArrayList<Map<String, Object>>)finalMap.get(Path.Locations.toString());

        //Location[] locations = new Location[ls.size()];
        //for (int i = 0; i < locations.length; i++) {
        //    locations[i] = new Location(getServer().getWorld(ls.get(i).get("world").toString()),
        //   (double) ls.get(i).get("x"),
        //        (double) ls.get(i).get("y"),
        //        (double) ls.get(i).get("z"),
        //        ((Double) ls.get(i).get("yaw")).floatValue(),
        //        ((Double) ls.get(i).get("pitch")).floatValue()
        //    );
        //}

        this.data = new SkyWarsMapData(
            (String) finalMap.get(Path.MapName.toString()),
            (String) finalMap.get(Path.GameSize.toString()),
                null, (int)finalMap.get(Path.MaxTeamCount.toString()),
            (int)finalMap.get(Path.MaxPlayersInTeam.toString()),
            (boolean)finalMap.get(Path.StillUnderDevelopment.toString())
        );

        return this.data;
    }


    public void registerCommands() {
        getCommand("start").setExecutor((CommandExecutor) new Command_start());
        getCommand("build").setExecutor((CommandExecutor)new Command_build());
        getCommand("sw").setExecutor((CommandExecutor)new Command_SkyWars());
        getCommand("create").setExecutor(new Command_create(Main.getInstance()));

    }

    public void registerEvents(SkyWarsMapData map) {
        kitListener = new KitListener();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new PlayerConnectionEvent(this, this.luckPerms), this);
        pluginManager.registerEvents((Listener) new KitListener(), this);
        pluginManager.registerEvents((Listener) new ProtectionListener(), this);
        pluginManager.registerEvents((Listener) new TeamListener(map, luckPerms), this);

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
