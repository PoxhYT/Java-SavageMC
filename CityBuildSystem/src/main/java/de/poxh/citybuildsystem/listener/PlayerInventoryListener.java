package de.cb.poxh.listener;

import de.cb.poxh.main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Random;

public class PlayerInventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().equalsIgnoreCase("§bTeleporter")) {
            if(event.getCurrentItem().getType() == Material.WITHER_SKELETON_SKULL) {
                player.closeInventory();
                player.performCommand("plot home");
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.sendMessage(Main.instance.prefix + "Du wurdest zu deinem §ePlot §7teleportiert.");
            }

            if(event.getCurrentItem().getType() == Material.GRASS) {
                player.closeInventory();
                player.performCommand("world teleport Farmwelt");
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            }
            event.setCancelled(true);
        }
    }
}
