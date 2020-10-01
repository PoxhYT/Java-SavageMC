package de.mlgrush.listener;

import de.mlgrush.enums.GameState;
import de.mlgrush.enums.LocationType;
import de.mlgrush.gamestates.GameStateHandler;
import de.mlgrush.main.Main;
import de.mlgrush.utils.PlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        PlayerManager playerManager = new PlayerManager(player);
        if (GameStateHandler.getGameState() == GameState.INGAME) {
            Location spawn1;
            Location spawn2;
            if (Main.getInstance().getConfigManager().isCacheLoader()) {
                spawn1 = Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_1);
                spawn2 = Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPAWN_2);
            } else {
                spawn1 = Main.getInstance().getLocationHandler().getLocationByFile(LocationType.SPAWN_1);
                spawn2 = Main.getInstance().getLocationHandler().getLocationByFile(LocationType.SPAWN_2);
            }
            if ((event.getBlock().getLocation().getBlockX() == spawn1.getBlockX() && event.getBlock().getLocation().getBlockY() == spawn1.getBlockY() && event
                    .getBlock().getLocation().getBlockZ() == spawn1.getBlockZ()) || (event.getBlock().getLocation().getBlockX() == spawn2.getBlockX() && event
                    .getBlock().getLocation().getBlockY() == spawn2.getBlockY() && event.getBlock().getLocation().getBlockZ() == spawn2.getBlockZ())) {
                event.setCancelled(true);
                player.sendMessage(Main.prefix + "§cDu darfst nicht auf Spawns bauen.");
                return;
            }
            if (Main.getInstance().getRegionManager().isInRegion(event.getBlock().getLocation())) {
                Main.getInstance().getMapResetHandler().addPlacedBlock(event.getBlock().getLocation());
            } else {
                event.setCancelled(true);
                player.sendMessage(Main.prefix + "§cDu darfst außerhalb des Spielbereiches nicht bauen!");
            }
        } else if ((GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) &&
                playerManager.getCurrentSetupBed() == null) {
            event.setCancelled(!playerManager.isInBuildMode());
        }
    }
}

