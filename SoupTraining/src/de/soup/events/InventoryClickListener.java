package de.soup.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getClickedInventory().getTitle().equals("§eTeleporter")) {
            if (event.getCurrentItem().getType() == Material.NETHER_STAR) {
                if(Bukkit.getWorld(String.valueOf(World.Environment.NETHER)).getName())
            }
        }
    }
}
