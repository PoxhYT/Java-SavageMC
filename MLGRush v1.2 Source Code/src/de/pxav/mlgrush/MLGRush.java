package de.pxav.mlgrush;

import de.pxav.mlgrush.commands.BuildCommand;
import de.pxav.mlgrush.commands.MainCommand;
import de.pxav.mlgrush.commands.SetupCommand;
import de.pxav.mlgrush.enums.GameState;
import de.pxav.mlgrush.gamestates.*;
import de.pxav.mlgrush.handler.PointsHandler;
import de.pxav.mlgrush.handler.ScoreboardHandler;
import de.pxav.mlgrush.handler.TabListHandler;
import de.pxav.mlgrush.handler.TeamHandler;
import de.pxav.mlgrush.items.InventoryManager;
import de.pxav.mlgrush.listener.*;
import de.pxav.mlgrush.maps.LocationHandler;
import de.pxav.mlgrush.maps.MapResetHandler;
import de.pxav.mlgrush.maps.RegionManager;
import de.pxav.mlgrush.maps.WorldManager;
import de.pxav.mlgrush.settings.ConfigManager;
import de.pxav.mlgrush.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MLGRush extends JavaPlugin {

    private final String PREFIX = "§8[§3MLGRush§8] §r";

    /* CLASS INSTANCES */
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
    private StartCountDown startCountDown = new StartCountDown();
    private MapResetHandler mapResetHandler = new MapResetHandler();
    private ConfigManager configManager = new ConfigManager();
    private RegionManager regionManager = new RegionManager();

    private static MLGRush instance;

    @Override
    public void onEnable() {
        setInstance(this);
        GameStateHandler.setGameState(GameState.LOBBY);
        GameStateHandler.setAllowMove(true);

        this.loadConfigurations();
        this.registerCommands();
        this.registerEvents();

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            this.getWorldManager().prepareMaps();
            this.getScoreboardHandler().startUpdater();

        }, 4);

    }

    @Override
    public void onDisable() {
        this.getMapResetHandler().resetMap(true);
        setInstance(null);
    }

    private void loadConfigurations() {
        this.getConfigManager().loadFile();
        this.getConfigManager().loadSettings();

        this.getLocationHandler().loadFile();
        if(this.getConfigManager().isCacheLoader())
            this.getLocationHandler().loadLocations();
    }

    private void registerCommands() {
        new SetupCommand("setup");
        new MainCommand("mlgrush");
        new BuildCommand("build");
    }

    private void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new ServerListPingListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        pluginManager.registerEvents(new WeatherChangeListener(), this);
        pluginManager.registerEvents(new FoodLevelChangeListener(), this);
        pluginManager.registerEvents(new CraftItemListener(), this);
        pluginManager.registerEvents(new EntityDamageByEntityListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new PlayerLoginListener(), this);
        pluginManager.registerEvents(new PlayerDropItemListener(), this);
        if(this.getConfigManager().isUseChatFormat())
            pluginManager.registerEvents(new AsyncPlayerChatListener(), this);
    }

    public static MLGRush getInstance() {
        return instance;
    }

    private static void setInstance(final MLGRush instance) {
        MLGRush.instance = instance;
    }

    public IdleCountdown getIdleCountdown() {
        return idleCountdown;
    }

    public LobbyCountdown getLobbyCountdown() {
        return lobbyCountdown;
    }

    public String getPrefix() {
        return PREFIX;
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

    public StartCountDown getStartCountDown() {
        return startCountDown;
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
}
