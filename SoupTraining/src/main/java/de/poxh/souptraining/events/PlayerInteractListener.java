package de.soup.events;

import de.soup.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            if(event.getItem().getType() == Material.COMPASS) {
                getTeleportInv(player);
                if (event.getItem().getType() == null) {
                    return;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void getTeleportInv(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§eTeleporter");

        for (int i = 0; i < 27; i++)
            inventory.setItem(i, new ItemManager(Material.STAINED_GLASS_PANE,(short)7).setDisplayName("§1 ").build());
        inventory.setItem(13, new ItemManager(Material.NETHER_STAR).setDisplayName("§cSpawn").build());
        inventory.setItem(15, new ItemManager(Material.CHEST).setDisplayName("§eShop").build());
        inventory.setItem(11, new ItemManager(Material.PAPER).setDisplayName("§aSchwierigkeit").build());
        player.openInventory(inventory);
    }

}
