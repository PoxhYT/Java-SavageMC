package de.lobby.main;

import de.lobby.commands.Command_Reward;
import de.lobby.commands.Command_build;
import de.lobby.commands.Command_lobby;
import de.lobby.commands.Command_setreward;
import de.lobby.listener.*;
import de.lobby.mysql.ConnectionManager;
import de.lobby.mysql.MySQL;
import de.lobby.utils.FileManager;
import de.lobby.utils.RewardManager;
import de.lobby.utils.SBManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static Main instance;

    public static String prefix = "§8• §eSavageMC §8⎟ §7";
    public static String prefix2 = "§8• §r§eCoinsystem §8⎟ §7";
    public static String noPerms = Main.prefix + "§r§cDu hast keine Rechte§7, §r§cum dies auszuführen§7.";
    public static String notFound = Main.prefix + "§r§cDieser Spieler wurde nicht gefunden§7!";
    public static String silenthub = "§8• §r§cSilentHub §8⎟ §r§7";
    public static String buyRank = Main.prefix + "Kaufe dir einen §r§cRang §r§7unter: https://shop.savagemc.net";
    public static String youtube = Main.prefix + "Du §r§cbenötigst §r§7mindestens den §r§5YouTuber§r§7-§r§cRang§r§7, §r§7um die §r§cSilentLobby §r§7betreten zu können.";
    public static String abruferror = Main.prefix + "§r§cFehler beim abrufen...";
    public static String noCoins = Main.prefix + "§r§cDu hast nicht genügend Coins.";

    public RewardManager rewardManager;
    public boolean broadcast;
    public static List<Player> build = new ArrayList<>();
    public static boolean SilentLobby = FileManager.getBoolean("Main.SilentLobby");
    public static ConnectionManager connection;
    public static ConnectionManager connectionss;

    @Override
    public void onEnable() {
        instance = this;
        FileManager.createFile();
        getServer().getMessenger().registerOutgoingPluginChannel((Plugin)this, "BungeeCord");
        try {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.hasPermission("lobby.admin"))
                    all.sendMessage(Main.prefix + "§r§7Das §r§eLobbySystem §r§7wurde §r§aerfolgreich §r§7gestartet!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.hasPermission("lobby.admin")) {
                    all.sendMessage(Main.prefix + "§r§7Das §r§eLobbySystem §7konnte nicht geladen werden!");
                    all.sendMessage(Main.prefix + "§r§cKontaktiere einen Developer!");
                }
            }
        }
        connection = new ConnectionManager("localhost", "root", FileManager.getString("MySQL.Passwort"), "lobby", "3306", true);
        connection.connect();
        connection.update("CREATE TABLE IF NOT EXISTS SilentLobbyAutoConnect(UUID varchar(64), Name varchar(64), SilentLobbyAutoConnect int);");
        connection.update("CREATE TABLE IF NOT EXISTS PremiumLobbyAutoConnect(UUID varchar(64), Name varchar(64), PremiumLobbyAutoConnect int);");
        connection.update("CREATE TABLE IF NOT EXISTS TeamLobbyAutoConnect(UUID varchar(64), Name varchar(64), PremiumLobbyAutoConnect int);");
        MySQL.connect();
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "§r§7Das §ePlugin §7wurde §aerfolgreich §7gestartet!");


        //Listener
        getServer().getPluginManager().registerEvents((Listener)new PlayerJoinQuitListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PlayerChatListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new ProtectionListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new InteractListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new InventoryClickListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PlayerLoginListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new ChestLotteryListener(), (Plugin)this);





        //Commands
        getCommand("help").setExecutor((CommandExecutor)new Command_lobby());
        getCommand("build").setExecutor((CommandExecutor)new Command_build());
        getCommand("setreward").setExecutor((CommandExecutor)new Command_setreward());


    }

    public static void scoreCD() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {

            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    SBManager.updateScoreboard(all);
                }
            }
        }, 0, 1);
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel((Plugin)this);
        connection.close();
        MySQL.disconnect();
    }

    public static Main getInstance() {
        return instance;
    }
}
