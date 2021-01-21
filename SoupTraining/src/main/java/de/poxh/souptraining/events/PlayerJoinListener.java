package de.poxh.souptraining.events;

import de.poxh.souptraining.main.Main;
import de.poxh.souptraining.manager.ItemManager;
import de.poxh.souptraining.manager.SBManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Random;

public class PlayerJoinListener implements Listener {

    private static String displayName;

    private static int number;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();

        String uuid = player.getUniqueId().toString();
//        CoinsAPI.createPlayer(uuid);
        Random random = new Random();
        for (int counter=1; counter<=100;counter++) {
            number = random.nextInt(950);
            event.setJoinMessage(Main.prefix + number);
        }
//        CoinsAPI.addCoins(player.getUniqueId().toString(), number);
        getLobbyItems(player);
        SBManager.setLobbyBoard(player);
        Main.scoreCD();
        player.teleport(de.soup.manager.LocationManager.getSpawn("Lobby"));
        for (Player players : Bukkit.getOnlinePlayers())
            players.hidePlayer(players);

        if(player.hasPermission("server.owner")) {
            event.setJoinMessage(Main.prefix + "§8» §4Owner §7❘ " + player.getName() + " §7hat den §eServer §7betreten!");
        }
        if(player.hasPermission("server.mod")) {
            event.setJoinMessage(Main.prefix + "§8» §9Mod §7❘ " + player.getName() + " §7hat den §eServer §7betreten!");
        }
        if(player.hasPermission("server.sup")) {
            event.setJoinMessage(Main.prefix + "§8» §3Sup §7❘ " + player.getName() + " §7hat den §eServer §7betreten!");
        }
        if(player.hasPermission("server.dev")) {
            event.setJoinMessage(Main.prefix + "§8» §bDev §7❘ " + player.getName() + " §7hat den §eServer §7betreten!");
        }
        if(player.hasPermission("server.content")) {
            event.setJoinMessage(Main.prefix + "§8» §cContent §7❘ " + player.getName() + " §7hat den §eServer §7betreten!");
        }
        if(player.hasPermission("server.builder")) {
            event.setJoinMessage(Main.prefix + "§8» §2Builder §7❘ " + player.getName() + " §7hat den §eServer §7betreten!");
        }
        if(player.hasPermission("server.freund")) {
            event.setJoinMessage(Main.prefix + "§8» §aFreund §7❘ " + player.getName() + " §7hat den §eServer §7betreten!");
        }
        if(player.hasPermission("server.youtuber")) {
            event.setJoinMessage(Main.prefix + "§8» §5YouTuber §7❘ " + player.getName() + " §7hat den §eServer §7betreten!");
        }
        if(player.hasPermission("server.jryoutuber")) {
            event.setJoinMessage(Main.prefix + "§8» §9Mod §7❘ " + player.getName() + " §7hat den §eServer §7betreten!");
        }else
            event.setJoinMessage(Main.prefix + "§8» §7Spieler ❘ " + player.getName() + " §7hat den §eServer §7betreten!");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat("§4Owner §8❘ §7" + player.getName() + " §7: " + event.getMessage());
    }

    private void getLobbyItems(Player player) {
        player.getInventory().setItem(4, new ItemManager(Material.COMPASS).setDisplayName("§8» §eTeleporter").build());
        player.getInventory().setItem(6, new ItemManager(Material.CHEST).setDisplayName("§8» §eInventory").build());
    }

    private void getRandomNumber(Player player) {

    }
}
