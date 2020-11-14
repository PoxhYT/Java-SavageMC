package main.java.de.xenodev.cbs.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class InvHeldListener implements Listener {

    @EventHandler
    public void invHeld(PlayerItemHeldEvent e){
        Player p = e.getPlayer();
        p.playSound(p.getLocation(), Sound.NOTE_STICKS, 50, 1);
    }

}
