package de.sw.listener;

import de.sw.api.LocationAPI;
import de.sw.countdown.LobbyCountdown;
import de.sw.main.Main;
import de.sw.manager.InventoryManager;
import de.sw.manager.KitManager;
import de.sw.manager.SBManager;
import de.sw.manager.UtilsManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerJoinListener implements Listener {

    public static Main instance = Main.instance;

    private File file = new File("plugins/SkyWars", "Config.yml");

    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    private SBManager sbManager = new SBManager();

    private LobbyCountdown countdown = new LobbyCountdown();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Main.inventoryManager.setLobbyInventory(player);
        sbManager.setLobbyBoard(player);
        player.teleport(LocationAPI.getSpawn("Lobby"));
        if(UtilsManager.onlinePlayers.size() < UtilsManager.minPlayers) {
            UtilsManager.onlinePlayers.add(player);
            player.setMaxHealth(20.0D);
            player.setHealth(20.0D);
            player.setFoodLevel(20);
            if(UtilsManager.onlinePlayers.size() >= UtilsManager.minPlayers) {
                countdown.start();
                if(LobbyCountdown.isIdling)
                    countdown.stopIdle();
            } else {
                countdown.idle();
            }
            if(player.hasPermission("server.owner")) {
                event.setJoinMessage(Main.prefix + "§8» §4Owner §7❘ " + player.getName() + " §7hat das §eSpiel §7betreten!");
            }
            if(player.hasPermission("server.mod")) {
                event.setJoinMessage(Main.prefix + "§8» §9Mod §7❘ " + player.getName() + " §7hat das §eSpiel §7betreten!");
            }
            if(player.hasPermission("server.sup")) {
                event.setJoinMessage(Main.prefix + "§8» §3Sup §7❘ " + player.getName() + " §7hat das §eSpiel §7betreten!");
            }
            if(player.hasPermission("server.dev")) {
                event.setJoinMessage(Main.prefix + "§8» §bDev §7❘ " + player.getName() + " §7hat das §eSpiel §7betreten!");
            }
            if(player.hasPermission("server.builder")) {
                event.setJoinMessage(Main.prefix + "§8» §2Builder §7❘ " + player.getName() + " §7hat das §eSpiel §7betreten!");
            }
            if(player.hasPermission("server.youtuber")) {
                event.setJoinMessage(Main.prefix + "§8» §5YouTuber §7❘ " + player.getName() + " §7hat das §eSpiel §7betreten!");
            }
            if(player.hasPermission("server.freund")) {
                event.setJoinMessage(Main.prefix + "§8» §aFreund §7❘ " + player.getName() + " §7hat das §eSpiel §7betreten!");
            }
            if(player.hasPermission("server.savage")) {
                event.setJoinMessage(Main.prefix + "§8» §5Savage §7❘ " + player.getName() + " §7hat das §eSpiel §7betreten!");
            }
            if(player.hasPermission("server.ultra")) {
                event.setJoinMessage(Main.prefix + "§8» §dUltra §7❘ " + player.getName() + " §7hat das §eSpiel §7betreten!");
            }
            if(player.hasPermission("server.premium+")) {
                event.setJoinMessage(Main.prefix + "§8» §ePremium§7+ ❘ " + player.getName() + " §7hat das §eSpiel §7betreten!");
            }
            if(player.hasPermission("server.premium")) {
                event.setJoinMessage(Main.prefix + "§8» §6Premium §7❘ " + player.getName() + " §7hat das §eSpiel §7betreten!");
            }
        }else {
            event.setJoinMessage(null);
            UtilsManager.spectator.add(player);
        }
    }
}
