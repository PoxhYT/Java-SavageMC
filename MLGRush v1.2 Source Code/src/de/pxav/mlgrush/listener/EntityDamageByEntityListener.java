package de.pxav.mlgrush.listener;

// The project Stream - MLGRush is developed and updated by PXAV.
// You are not allowed to claim this as your own, give it to 
// others or even sell it.
//
// Contact me on:
// YouTube: https://www.youtube.com/channel/UCtXSAGTwurKVeEbwEKMlFog
// Twitter: https://twitter.com/OrigPXAV
// 
// (c) 2018 PXAV     

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.enums.GameState;
import de.pxav.mlgrush.enums.LocationType;
import de.pxav.mlgrush.enums.TeamType;
import de.pxav.mlgrush.gamestates.GameStateHandler;
import de.pxav.mlgrush.utils.PlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {

            final Player damages = (Player) event.getDamager();
            final Player player = (Player) event.getEntity();

            if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
                event.setCancelled(true);
            } else if (GameStateHandler.getGameState() == GameState.INGAME
                    && MLGRush.getInstance().getConfigManager().isDamageCheck()) {

                Location spawn1;
                Location spawn2;
                if(MLGRush.getInstance().getConfigManager().isCacheLoader()) {
                    spawn1 = MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_1);
                    spawn2 = MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_2);
                } else {
                    spawn1 = MLGRush.getInstance().getLocationHandler().getLocationByFile(LocationType.SPAWN_1);
                    spawn2 = MLGRush.getInstance().getLocationHandler().getLocationByFile(LocationType.SPAWN_2);
                }

                if (MLGRush.getInstance().getTeamHandler().getPlayerTeam(damages) == TeamType.TEAM_1) {
                    if(player.getLocation().getBlockY() >= spawn2.getBlockY()) {
                        event.setCancelled(true);
                        damages.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu kannst §7" + player.getName() + " §cnicht am Spawn schlagen.");
                    }
                } else if (MLGRush.getInstance().getTeamHandler().getPlayerTeam(damages) == TeamType.TEAM_2) {
                    if(player.getLocation().getBlockY() >= spawn1.getBlockY()) {
                        event.setCancelled(true);
                        damages.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu kannst §7" + player.getName() + " §cnicht am Spawn schlagen.");
                    }
                }
            }
        } else
            event.setCancelled(true);
    }

}
