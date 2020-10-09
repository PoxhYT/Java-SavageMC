package de.sw.listener;

import de.sw.manager.UtilsManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
            if (UtilsManager.canMove) {
                e.setCancelled(false);
            } else {
                e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), e.getFrom().getX(), e.getFrom().getY(), e.getFrom().getZ()));
            }
        } else {
            e.setCancelled(false);
        }
    }
}



