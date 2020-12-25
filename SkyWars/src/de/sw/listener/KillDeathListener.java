package de.sw.listener;

import de.sw.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class KillDeathListener implements Listener {


    //Damage event
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        try {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (event.getDamager() instanceof Player) {
                    Player damager = (Player) event.getDamager();
                    String TeamDamager = getExactTeam(damager);
                    String TeamPlayer = getExactTeam(player);
                    if (TeamDamager.equalsIgnoreCase(TeamPlayer)) {
                        event.setCancelled(true);
                    } else {
                        event.setCancelled(false);
                    }
                }
            }
        } catch (NullPointerException e) {}
    }

    public String getExactTeam(Player player) {
        return Main.teamManagerMap.get(player.getUniqueId());
    }
}
