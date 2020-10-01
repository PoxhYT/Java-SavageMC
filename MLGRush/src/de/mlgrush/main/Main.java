package de.mlgrush.main;

import de.mlgrush.api.MySQL;
import de.mlgrush.commands.*;
import de.mlgrush.enums.GameState;
import de.mlgrush.gamestates.*;
import de.mlgrush.handler.PointsHandler;
import de.mlgrush.handler.ScoreboardHandler;
import de.mlgrush.handler.TabListHandler;
import de.mlgrush.handler.TeamHandler;
import de.mlgrush.items.InventoryManager;
import de.mlgrush.listener.*;
import de.mlgrush.maps.LocationHandler;
import de.mlgrush.maps.MapResetHandler;
import de.mlgrush.maps.RegionManager;
import de.mlgrush.maps.WorldManager;
import de.mlgrush.mysql.Ranking;
import de.mlgrush.utils.ConfigManager;
import de.mlgrush.utils.PlayerMoveScheduler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private IdleCountdown idleCountdown = new IdleCountdown();

    private LobbyCountdown lobbyCountdown = new LobbyCountdown();

    private WorldManager worldManager = new WorldManager();

    private LocationHandler locationHandler = new LocationHandler();

    private EndingCountdown endingCountdown = new EndingCountdown();

    private InventoryManager inventoryManager = new InventoryManager();

    private TeamHandler teamHandler = new TeamHandler();

    private ScoreboardHandler scoreboardHandler = new ScoreboardHandler();

    private TabListHandler tabListHandler = new TabListHandler();

    private PointsHandler pointsHandler = new PointsHandler();

    private PlayerMoveScheduler playerMoveScheduler = new PlayerMoveScheduler();

    private StartCountdown startCountdown = new StartCountdown();

    private MapResetHandler mapResetHandler = new MapResetHandler();

    private ConfigManager configManager = new ConfigManager();

    private RegionManager regionManager = new RegionManager();

    public static String prefix = "§8• §eMLGRush §8❘ §7";

    public static FileConfiguration configuration;

    private static Main instance;

    public static MySQL mySQL;

    @Override
    public void onEnable() {
        GameStateHandler.setGameState(GameState.LOBBY);
        GameStateHandler.setAllowMove(true);
        loadConfigurations();
        ConnectMySQL();
        registerEvents();
        registerCommands();
        instance = this;

        Bukkit.getConsoleSender().sendMessage(prefix + "§ePlugin §7wurde §agestartet§7!");

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            this.getWorldManager().prepareMaps();
            this.getScoreboardHandler().startUpdater();

        }, 4);

    }

    @Override
    public void onDisable() {
        this.getMapResetHandler().resetMap(true);

    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new Stats(), this);
        pluginManager.registerEvents((Listener)new PlayerJoinListener(), (Plugin)this);
        pluginManager.registerEvents((Listener)new ServerListPingListener(), (Plugin)this);
        pluginManager.registerEvents((Listener)new BlockBreakListener(), (Plugin)this);
        pluginManager.registerEvents((Listener)new PlayerQuitListener(), (Plugin)this);
        pluginManager.registerEvents((Listener)new PlayerInteractListener(), (Plugin)this);
        pluginManager.registerEvents((Listener)new InventoryClickListener(), (Plugin)this);
        pluginManager.registerEvents((Listener)new BlockPlaceListener(), (Plugin)this);
        pluginManager.registerEvents((Listener)new EntityDamageByEntityListener(), (Plugin)this);
        pluginManager.registerEvents((Listener)new PlayerLoginListener(), (Plugin)this);
    }

    private void registerCommands() {
        getCommand("stats").setExecutor(new Command_stats());
        getCommand("shop").setExecutor(new Command_shop());
        new Command_Build("build");
        new Command_setup("setup");
        new Command_MLGRush("mlgrush");
    }

    private void ConnectMySQL() {
        mySQL = new MySQL("localhost", "mlgrush", "root", "bruh1234");
        mySQL.update("CREATE TABLE IF NOT EXISTS Stats(UUID varchar(64), KILLS int, DEATHS int, WINS int)");
    }

    private void loadConfigurations() {
        getConfigManager().loadFile();
        getConfigManager().loadSettings();
        getLocationHandler().loadFile();
        if (getConfigManager().isCacheLoader())
            getLocationHandler().loadLocations();
    }

    public static Main getInstance() {
        return instance;
    }

    private static void setInstance(final Main instance) {
        Main.instance = instance;
    }

    public IdleCountdown getIdleCountdown() {
        return idleCountdown;
    }

    public LobbyCountdown getLobbyCountdown() {
        return lobbyCountdown;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public LocationHandler getLocationHandler() {
        return locationHandler;
    }

    public EndingCountdown getEndingCountdown() {
        return endingCountdown;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public TeamHandler getTeamHandler() {
        return teamHandler;
    }

    public TabListHandler getTabListHandler() {
        return tabListHandler;
    }

    public ScoreboardHandler getScoreboardHandler() {
        return scoreboardHandler;
    }

    public PointsHandler getPointsHandler() {
        return pointsHandler;
    }

    public PlayerMoveScheduler getPlayerMoveScheduler() {
        return playerMoveScheduler;
    }

    public StartCountdown getStartCountdown() {
        return startCountdown;
    }

    public MapResetHandler getMapResetHandler() {
        return mapResetHandler;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public RegionManager getRegionManager() {
        return regionManager;
    }

    public static String getPrefix() {
        return prefix;
    }
}



