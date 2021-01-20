package de.cb.poxh.listener;

import de.cb.poxh.main.Main;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
            if (!Main.instance.moveMap.contains(e.getPlayer())) {
                e.setCancelled(false);
            } else {
                e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), e.getFrom().getX(), e.getFrom().getY(), e.getFrom().getZ()));
            }
        } else {
            e.setCancelled(false);
        }
    }
}
