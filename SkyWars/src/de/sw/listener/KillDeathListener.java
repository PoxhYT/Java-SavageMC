package de.sw.listener;

import com.rosemite.services.helper.Log;
import com.rosemite.services.models.skywars.PlayerSkywarsStats;
import de.sw.main.Main;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

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

    //Death event
    @EventHandler
    public void onDeath(EntityDamageEvent.DamageCause event) {

    }


    public String getExactTeam(Player player) {
        return Main.teamManagerMap.get(player.getUniqueId());
    }
}
