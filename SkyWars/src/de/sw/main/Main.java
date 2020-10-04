package de.sw.main;

import de.anweisung.premiumkickapi.PremiumKick;
import de.sw.commands.Command_leave;
import de.sw.commands.Command_setup;
import de.sw.listener.PlayerInteractListener;
import de.sw.listener.PlayerInventoryClickListener;
import de.sw.listener.PlayerJoinListener;
import de.sw.listener.TeamListener;
import de.sw.manager.InventoryManager;
import de.sw.manager.KitManager;
import de.sw.manager.SBManager;
import de.sw.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
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

    public static TeamManager teamManager;

    public int playersInTeam;

    public int maxPlayers = yamlConfiguration.getInt("maxPlayers");

    public static InventoryManager inventoryManager = new InventoryManager();

    public String allKitsPerm;

    public SBManager sbManager = new SBManager();

    public void onEnable() {
        KitManager kitManager = new KitManager("§eStandard", new String[] {"§eYARRAK"}, Material.BAKED_POTATO, 2);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        registerEvents();
        loadConfig();
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(prefix + "§eDas Plugin wurde erfolgreich gestartet!!!!");
        PremiumKick.allowPremiumKick();

    }

    public void registerCommands() {
        new Command_setup("setup");
        getCommand("leave").setExecutor((CommandExecutor)new Command_leave(this));
    }

    public void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new PlayerJoinListener(), this);
        pluginManager.registerEvents((Listener) new PlayerInteractListener(), this);
        pluginManager.registerEvents((Listener) new TeamListener(), this);
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

    public static Main getInstance() {
        return instance;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
