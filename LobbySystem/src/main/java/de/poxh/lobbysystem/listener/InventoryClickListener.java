package de.poxh.lobby.listener;

import de.poxh.lobby.api.LocationAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            if(event.getInventory().getTitle().equalsIgnoreCase("§bNavigator")) {
                if(event.getCurrentItem().getType() == Material.NETHER_STAR) {
                    teleportPlayer(player, "Lobby");
                }
                if(event.getCurrentItem().getType() == Material.GRASS) {
                    teleportPlayer(player, "SkyWars");
                }
                if(event.getCurrentItem().getType() == Material.STICK) {
                    teleportPlayer(player, "Mlg-Rush");
                }
                if(event.getCurrentItem().getType() == Material.BED) {
                    teleportPlayer(player, "BedWars");
                }
            }
        }catch (NullPointerException e){}
    }

    private static void teleportPlayer(Player player, String name) {
        player.teleport(LocationAPI.getSpawn(name));
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
        player.closeInventory();
    }
}
