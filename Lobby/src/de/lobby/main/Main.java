package de.lobby.main;

import de.lobby.api.ScoreboardAPI;
import de.lobby.commands.Command_setup;
import de.lobby.listener.PlayerInteractListener;
import de.lobby.listener.PlayerJoinListener;
import de.lobby.manager.InventoryManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static Scoreboard sb;
    public static Main instance;
    public static String prefix = "§eSavageMC §7❘ §7";
    public static String noPerms = prefix + "§cDazu hast du nicht genügend Rechte!";
    public static LuckPerms luckPerms;
    public static InventoryManager inventoryManager = new InventoryManager();
    public static List<Player> build = new ArrayList<>();

    @Override
    public void onEnable() {

        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        this.instance = this;
        registerEvents();
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "§eDas Plugin wurde erfolgreich gestartet!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "§cDas Plugin wurde erfolgreich beendet!");
    }

    private void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents((Listener) new PlayerJoinListener(this, this.luckPerms), this);
        manager.registerEvents((Listener) new PlayerInteractListener(), this);
    }

    private void registerCommands() {
        getCommand("setup").setExecutor((CommandExecutor) new Command_setup());
    }

    public static void scoreCD() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin)Main.getInstance(), new Runnable() {

            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    ScoreboardAPI.updateScoreboard(all);
                }
            }
        }, 0, 1);
    }

    public static Main getInstance() {
        return instance;
    }
}
