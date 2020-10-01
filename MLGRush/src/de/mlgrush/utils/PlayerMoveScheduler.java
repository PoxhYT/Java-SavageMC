package de.mlgrush.utils;

import de.mlgrush.enums.LocationType;
import de.mlgrush.gamestates.GameStateHandler;
import de.mlgrush.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerMoveScheduler {

    private int taskID;

    public void startListening() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            for (Player current : Bukkit.getOnlinePlayers()) {

                if (!GameStateHandler.isAllowMove()) {
                    current.getLocation().setX(current.getLocation().getX());
                    current.getLocation().setY(current.getLocation().getY());
                    current.getLocation().setZ(current.getLocation().getZ());
                    continue;
                }

                Location pos1;
                if(Main.getInstance().getConfigManager().isCacheLoader())
                    pos1 = Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.POS_1);
                else pos1 = Main.getInstance().getLocationHandler().getLocationByFile(LocationType.POS_1);

                if (current.getLocation().getY() <= pos1.getY()) {
                    final PlayerManager playerManager = new PlayerManager(current);
                    playerManager.addRoundDeath();
                    playerManager.teleportToTeamIsland();
                    playerManager.resetPlayer();
                    Main.getInstance().getInventoryManager().giveIngameItems(current);
                    current.playSound(current.getLocation(), Sound.ORB_PICKUP, 3, 1);
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> current.setHealth(20), 2);
                }

                if (!Main.getInstance().getRegionManager().isInRegion(current.getLocation())) {
                    final PlayerManager playerManager = new PlayerManager(current);
                    playerManager.addRoundDeath();
                    playerManager.teleportToTeamIsland();
                    current.playSound(current.getLocation(), Sound.NOTE_BASS, 3, 1);
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> current.setHealth(20), 2);
                    current.sendMessage(Main.prefix + "§cDu darfst dich nicht außerhalb des Spielbereichs bewegen.");
                }
            }
        }, 0L, 2L);
    }

    public void stopListening() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}

