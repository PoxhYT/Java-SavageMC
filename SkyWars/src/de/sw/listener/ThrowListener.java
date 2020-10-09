package de.sw.listener;

import de.sw.manager.UtilsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ThrowListener implements Listener {
    @EventHandler
    public void onThrow(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        try {
            if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && (
                    p.getItemInHand().getType() == Material.ENDER_PEARL || p.getItemInHand().getType() == Material.EGG) &&
                    !UtilsManager.canThrow) {
                e.setCancelled(true);
                p.updateInventory();
            }
        } catch (NullPointerException nullPointerException) {}
    }
}

