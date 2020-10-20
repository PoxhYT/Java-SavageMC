package de.lobby.listener;

import de.lobby.api.LocationAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getInventory().getType().equals("§eNavigator")) {
            if(event.getCurrentItem().getType() == Material.NETHER_STAR) {
                player.teleport(LocationAPI.getSpawn("spawn"));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
            }
            if(event.getCurrentItem().getType() == Material.GRASS) {
                player.teleport(LocationAPI.getSpawn("skywars"));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
            }
            if(event.getCurrentItem().getType() == Material.BED) {
                player.teleport(LocationAPI.getSpawn("bedwars"));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
            }
            if(event.getCurrentItem().getType() == Material.STICK) {
                player.teleport(LocationAPI.getSpawn("mlgrush"));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
            }
            if(event.getCurrentItem().getType() == Material.GOLDEN_APPLE) {
                player.teleport(LocationAPI.getSpawn("UHC"));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
            }
            if(event.getCurrentItem().getType() == Material.GOLDEN_CARROT) {
                player.teleport(LocationAPI.getSpawn("bedwars"));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
            }
            if(event.getCurrentItem().getType() == Material.MUSHROOM_SOUP) {
                player.teleport(LocationAPI.getSpawn("souptraining"));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
            }
        }
    }
}
