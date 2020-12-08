package de.sw.listener;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerCauseListener implements Listener {

    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        try {
            Player player = (Player) event.getEntity();

            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) {
                Bukkit.broadcastMessage(Main.prefix + player.getDisplayName() + " §7ist in die §cLeere §7gefallen!");
            } else if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                Bukkit.broadcastMessage(Main.prefix + player.getDisplayName() + " §7ist an §cFallschaden §7gestorben!");
            }
        }catch (NullPointerException e){}
    }
}
