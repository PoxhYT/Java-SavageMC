package com.poxh.listener;

import com.poxh.api.LocationAPI;
import com.poxh.main.Main;
import com.poxh.manager.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        //Setting the Lobby for the Server
        if(event.getMessage().equalsIgnoreCase(".setLobby")) {
            event.setCancelled(true);
            LocationAPI.setSpawn("Lobby", player.getLocation());
            player.sendMessage(Main.prefix + "§eDie Lobby wurde erfolgreich gesetzt!");
            Main.existLobby = true;
            Bukkit.shutdown();
        }

        //open the Setup menu
        if(event.getMessage().equalsIgnoreCase(".setup")) {
            event.setCancelled(true);
            Main.inventoryManager.SetupInventory(player);
            player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
        }

    }
}
