package de.lobby.listener;

import de.lobby.main.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    private Main instance;

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getItem().getType() == Material.COMPASS) {
            instance.inventoryManager.openNavigatorInventory(player);
            player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
        }
    }
}
