package com.poxh.listener;

import com.poxh.api.LocationAPI;
import com.poxh.main.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(!Main.existLobby) {
            player.sendMessage(Main.prefix + "§cBitte setzte die Lobby damit das System funktioniert!");
            player.sendMessage(Main.prefix + "§cBitte schreibe in den Chat .setLobby damit die Lobby gesetzt wird!");
            player.sendMessage(Main.prefix + "§cDer Server startet neu nachdem die Lobby gesetzt wurde!");
        } else {
            player.teleport(LocationAPI.getSpawn("Lobby"));
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
            Main.inventoryManager.LobbyInventory(player);
        }
    }
}
