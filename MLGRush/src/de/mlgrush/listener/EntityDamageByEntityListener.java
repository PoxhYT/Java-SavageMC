package de.mlgrush.listener;

import de.mlgrush.enums.GameState;
import de.mlgrush.enums.LocationType;
import de.mlgrush.enums.TeamType;
import de.mlgrush.gamestates.GameStateHandler;
import de.mlgrush.main.Main;
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
                    && Main.getInstance().getConfigManager().isDamageCheck()) {

                Location spawn1;
                Location spawn2;
                if(Main.getInstance().getConfigManager().isCacheLoader()) {
                    spawn1 = Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_1);
                    spawn2 = Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_2);
                } else {
                    spawn1 = Main.getInstance().getLocationHandler().getLocationByFile(LocationType.SPAWN_1);
                    spawn2 = Main.getInstance().getLocationHandler().getLocationByFile(LocationType.SPAWN_2);
                }

                if (Main.getInstance().getTeamHandler().getPlayerTeam(damages) == TeamType.TEAM_1) {
                    if(player.getLocation().getBlockY() >= spawn2.getBlockY()) {
                        event.setCancelled(true);
                        damages.sendMessage(Main.getInstance().getPrefix() + "§cDu kannst §7" + player.getName() + " §cnicht am Spawn schlagen.");
                    }
                } else if (Main.getInstance().getTeamHandler().getPlayerTeam(damages) == TeamType.TEAM_2) {
                    if(player.getLocation().getBlockY() >= spawn1.getBlockY()) {
                        event.setCancelled(true);
                        damages.sendMessage(Main.getInstance().getPrefix() + "§cDu kannst §7" + player.getName() + " §cnicht am Spawn schlagen.");
                    }
                }
            }
        } else
            event.setCancelled(true);
    }

}

