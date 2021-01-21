package de.cb.poxh.main;

import com.mongodb.client.MongoDatabase;
import com.rosemite.helper.Log;
import com.rosemite.models.service.common.IService;
import com.rosemite.services.main.MainService;
import de.cb.poxh.api.WorldAPI;
import de.cb.poxh.commands.*;
import de.cb.poxh.commands.Player.Command_backPack;
import de.cb.poxh.commands.Player.Command_tpa;
import de.cb.poxh.commands.admin.Command_world;
import de.cb.poxh.commands.clan.Command_Clan;
import de.cb.poxh.commands.clan.Command_position;
import de.cb.poxh.commands.countdowns.Countdown_ProtectionTime;
import de.cb.poxh.commands.countdowns.Countdown_delay;
import de.cb.poxh.commands.countdowns.Countdown_teleport;
import de.cb.poxh.listener.*;
import de.cb.poxh.manager.auction.AuctionManager;
import de.cb.poxh.manager.player.SBManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class Main extends JavaPlugin {

    //Strings
    public String prefix = "§b§lCityBuild §7❘ §7» ";
    private AuctionManager manager = new AuctionManager(this);

    //Service
    public IService service = MainService.getService();
    private MongoDatabase mongoDatabase;

    //List
    public List<Player> moveMap = new ArrayList<>();
    public static HashMap<String, ItemStack[]> menu = new HashMap<>();

    //Objects
    public static Main instance;
    public static LuckPerms luckPerms;
    public static SBManager sbManager = new SBManager();
    public Countdown_delay countdown_delay = new Countdown_delay();
    public Countdown_ProtectionTime countdown_protectionTime = new Countdown_ProtectionTime();
    public WorldAPI worldAPI = new WorldAPI();

    //Files
    public static File file = new File("plugins/CityBuildSystem", "Worlds.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public static File data = new File("plugins/CityBuildSystem", "BackPackData.yml");
    public static FileConfiguration dataCFG = YamlConfiguration.loadConfiguration(data);


    @Override
    public void onEnable() {
        instance = this;
        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        registerCommands();
        registerListener();
        Bukkit.getConsoleSender().sendMessage(prefix + "§aPlugin wurde geladen!");
        this.mongoDatabase = mongoDatabase;
        new WorldCreator("Farmwelt").createWorld();
        this.saveDefaultConfig();

        if(!data.exists()) {
            try {
                data.createNewFile();
                Bukkit.getConsoleSender().sendMessage(prefix+ "§aAll datas in Data.yml has been saved");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + "§aData.yml wurde erfolgreich geladen");
        }

        if(dataCFG.contains("data")) {
            loadItems(dataCFG.getConfigurationSection("data"));
        }

        countdown_protectionTime.runTaskTimer(this, 0, 100);
    }

    @Override
    public void onDisable() {
        if(!menu.isEmpty()) {
            saveInv();
            Bukkit.getConsoleSender().sendMessage(prefix + "§aAll datas in Data.yml has been saved");
        }
    }

    public void saveInv() {
        for(Map.Entry<String, ItemStack[]> entry : menu.entrySet()) {
            dataCFG.set("data." + entry.getKey(), entry.getValue());
        }
        try {
            dataCFG.save(data);
            Bukkit.getConsoleSender().sendMessage(prefix + "§aAll datas in Data.yml has been saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadItems(ConfigurationSection configurationSection) {
        for (String key : configurationSection.getKeys(false)) {

            List<ItemStack> itemList = (List<ItemStack>) configurationSection.getList(key);

            if (itemList == null) {
                throw new IllegalStateException("No List found under this key: " + key);
            }

            ItemStack[] itemArray = itemList.toArray(new ItemStack[0]);
            menu.put(key, itemArray);

            Bukkit.getConsoleSender().sendMessage(prefix + "§aAll data has been restored");
        }
    }

    private void registerListener() {
        PluginManager managerPlugin = Bukkit.getPluginManager();
        managerPlugin.registerEvents(manager, this);
        managerPlugin.registerEvents(new PlayerJoinListener(), this);
        managerPlugin.registerEvents(new PlayerInventoryListener(), this);
        managerPlugin.registerEvents(new BlockBreakListener(), this);
        managerPlugin.registerEvents(new PlayerMoveListener(), this);
        managerPlugin.registerEvents(countdown_protectionTime, this);
        managerPlugin.registerEvents(new Command_backPack(), this);
    }

    private void registerCommands() {
        getCommand("clan").setExecutor(new Command_Clan());
        getCommand("pos").setExecutor(new Command_position());
        getCommand("ah").setExecutor(new Command_ah(manager));
        getCommand("cb").setExecutor(new Command_CB());
        getCommand("coins").setExecutor(new Command_Coins());
        getCommand("tpa").setExecutor(new Command_tpa());
        getCommand("shop").setExecutor(new Command_shopSetup());
        getCommand("world").setExecutor(new Command_world());
        getCommand("bp").setExecutor(new Command_backPack());
    }

    public static Main getInstance() {
        return instance;
    }
}
