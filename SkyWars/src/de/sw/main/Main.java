package de.sw.main;

import de.anweisung.premiumkickapi.PremiumKick;
import de.sw.commands.Command_setup;
import de.sw.kits.Kit;
import de.sw.listener.PlayerInteractListener;
import de.sw.listener.PlayerInventoryClickListener;
import de.sw.listener.PlayerJoinListener;
import de.sw.manager.InventoryManager;
import de.sw.manager.KitManager;
import de.sw.manager.SBManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    public static String prefix = "§8[§bSkyWars§8] §7";

    public static Main instance;

    private File file = new File("plugins/SkyWars", "Config.yml");

    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public int teams;

    public KitManager kitManager;

    public int playersInTeam;

    public int maxPlayers = yamlConfiguration.getInt("maxPlayers");

    public static InventoryManager inventoryManager = new InventoryManager();

    public String allKitsPerm;

    public SBManager sbManager = new SBManager();

    public void onEnable() {
        KitManager kitManager = new KitManager("§eStandard", new String[] {"§eYARRAK"}, Material.BAKED_POTATO, 2);
        System.out.println(kitManager);
        registerEvents();
        loadConfig();
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(prefix + "§eDas Plugin wurde erfolgreich gestartet!");
        PremiumKick.allowPremiumKick();

    }

    public void registerCommands() {
        new Command_setup("setup");
    }

    public void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new PlayerJoinListener(), this);
        pluginManager.registerEvents((Listener) new PlayerInteractListener(), this);pluginManager.registerEvents((Listener) new PlayerInteractListener(), this);
        pluginManager.registerEvents((Listener) new PlayerInventoryClickListener(), this);
    }

    public void loadConfig() {
        if(file.exists()) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§eDie Config wurde aktuallisiert!");
            return;
        }
        if(!file.exists()) {
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

    public static void scoreCD() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            for (Player all : Bukkit.getOnlinePlayers()) {
                SBManager.updateScoreboard(all);
            }
        }, 0, 1);
    }

    public static Main getInstance() {
        return instance;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
