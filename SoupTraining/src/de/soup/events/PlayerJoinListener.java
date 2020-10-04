package de.soup.events;

import de.soup.main.Main;
import de.soup.manager.ItemManager;
import de.soup.manager.LocationManager;
import de.soup.manager.SBManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private static String displayName;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        getLobbyItems(player);
        SBManager.setLobbyBoard(player);
        Main.scoreCD();
        player.teleport(LocationManager.getSpawn("Lobby"));
        for (Player players : Bukkit.getOnlinePlayers())
            players.hidePlayer(players);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat("§4Owner §8❘ §7" + player.getName() + " §7: " + event.getMessage());
    }

    private void getLobbyItems(Player player) {
        player.getInventory().setItem(4, new ItemManager(Material.COMPASS).setDisplayName("§8» §eTeleporter").build());
    }
}
