package de.pxav.mlgrush.listener;

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.enums.GameState;
import de.pxav.mlgrush.enums.LocationType;
import de.pxav.mlgrush.enums.TeamType;
import de.pxav.mlgrush.gamestates.GameStateHandler;
import de.pxav.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author PXAV
 */

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {

        final PlayerManager playerManager = new PlayerManager(event.getPlayer());
        final Player player = playerManager.getPlayer();
        final MLGRush instance = MLGRush.getInstance();

        if(playerManager.getCurrentSetupBed() == LocationType.BED_TOP_1) {
            event.setCancelled(true);
            instance.getLocationHandler().addLocation(LocationType.BED_TOP_1.toString(), event.getBlock().getLocation());
            playerManager.setCurrentSetupBed(LocationType.BED_BOTTOM_1);
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Schlage nun den §aunteren §7Teil des Bettes von Team " +
                    "§a#1§7.");
        } else if(playerManager.getCurrentSetupBed() == LocationType.BED_BOTTOM_1) {
            event.setCancelled(true);
            instance.getLocationHandler().addLocation(LocationType.BED_BOTTOM_1.toString(), event.getBlock().getLocation());
            playerManager.setCurrentSetupBed(null);
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Einrichtung des Bettes von Team §a#1§7 abgeschlossen.");
        } else if(playerManager.getCurrentSetupBed() == LocationType.BED_TOP_2) {
            event.setCancelled(true);
            instance.getLocationHandler().addLocation(LocationType.BED_TOP_2.toString(), event.getBlock().getLocation());
            playerManager.setCurrentSetupBed(LocationType.BED_BOTTOM_2);
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Schlage nun den §aunteren §7Teil des Bettes von Team " +
                    "§a#2§7.");
        } else if(playerManager.getCurrentSetupBed() == LocationType.BED_BOTTOM_2) {
            event.setCancelled(true);
            instance.getLocationHandler().addLocation(LocationType.BED_BOTTOM_2.toString(), event.getBlock().getLocation());
            playerManager.setCurrentSetupBed(null);
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Einrichtung des Bettes von Team §a#2§7 abgeschlossen.");
        }

        if (GameStateHandler.getGameState() == GameState.INGAME) {

            if (event.getBlock().getType() == Material.SANDSTONE && instance.getMapResetHandler().canBreak(event.getBlock().getLocation()) && MLGRush.getInstance().getRegionManager().isInRegion(event.getBlock().getLocation())) {
                event.setCancelled(true);
                event.getBlock().getLocation().getWorld().getBlockAt(event.getBlock().getLocation()).setType(Material.AIR);
            } else if (event.getBlock().getType() == Material.BED_BLOCK) {

                if (event.getBlock().getLocation().equals(instance.getLocationHandler().getLocationByFile(LocationType.BED_TOP_1))
                        || event.getBlock().getLocation().equals(instance.getLocationHandler().getLocationByFile(LocationType.BED_BOTTOM_1))) {

                    if (instance.getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_1) {
                        event.setCancelled(true);
                        player.sendMessage(instance.getPrefix() + "§cDu darfst dein eigenes Bett nicht abbauen.");
                    } else if (instance.getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_2) {
                        event.setCancelled(true);

                        playerManager.addRoundPoint();
                        playerManager.sendTitle("§a§l+", "§7Du hast ein Bett abgebaut.");
                        for (Player current : Bukkit.getOnlinePlayers()) {
                            final PlayerManager currentManager = new PlayerManager(current);
                            player.playSound(player.getLocation(), Sound.ANVIL_USE, 3, 1);
                            currentManager.teleportToTeamIsland();
                            MLGRush.getInstance().getInventoryManager().givePlayItems(current);
                            if(!current.getName().equalsIgnoreCase(player.getName()))
                                currentManager.sendTitle("§c§lACHTUNG", "§7Dein Bett wurde abgebaut");
                        }

                        MLGRush.getInstance().getMapResetHandler().resetMap(false);

                        Bukkit.getScheduler().runTaskLater(MLGRush.getInstance(), () -> {
                            if(playerManager.getRoundPoints() == MLGRush.getInstance().getPointsHandler().getMaxPoints()) {
                                MLGRush.getInstance().getPlayerMoveScheduler().stopListening();
                                MLGRush.getInstance().getEndingCountdown().start();
                                for (Player current : Bukkit.getOnlinePlayers()) {
                                    final PlayerManager currentManager = new PlayerManager(current);
                                    if(MLGRush.getInstance().getConfigManager().isCacheLoader())
                                        current.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY));
                                    else current.teleport(MLGRush.getInstance().getLocationHandler().getLocationByFile(String.valueOf(LocationType.LOBBY)));
                                    currentManager.sendTitle("§a§l" + player.getName(), "§7hat das Spiel gewonnen!" + "");
                                    current.playSound(current.getLocation(), Sound.LEVEL_UP, 10, 10);
                                    currentManager.resetPlayer();
                                    MLGRush.getInstance().getInventoryManager().givePlayerLobbyItems(current);
                                    current.getInventory().setItem(0, new ItemStack(Material.AIR));
                                }
                            }
                        }, 2);

                    }
                } else if (event.getBlock().getLocation().equals(instance.getLocationHandler().getLocationByFile(String.valueOf(LocationType.BED_TOP_2)))
                        || event.getBlock().getLocation().equals(instance.getLocationHandler().getLocationByFile(String.valueOf(LocationType.BED_BOTTOM_2)))) {

                    if (instance.getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_2) {
                        event.setCancelled(true);
                        player.sendMessage(instance.getPrefix() + "§cDu darfst dein eigenes Bett nicht abbauen.");
                    } else if (instance.getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_1) {
                        event.setCancelled(true);

                        playerManager.addRoundPoint();
                        playerManager.sendTitle("§a§l+", "§7Du hast ein Bett abgebaut." + " ");
                        for (Player current : Bukkit.getOnlinePlayers()) {
                            final PlayerManager currentManager = new PlayerManager(current);
                            player.playSound(player.getLocation(), Sound.ANVIL_USE, 3, 1);
                            currentManager.teleportToTeamIsland();
                            MLGRush.getInstance().getInventoryManager().givePlayItems(current);
                            if(!current.getName().equalsIgnoreCase(player.getName()))
                                currentManager.sendTitle("§c§lACHTUNG", "§7Dein Bett wurde abgebaut" + " ");
                        }

                        MLGRush.getInstance().getMapResetHandler().resetMap(false);

                        Bukkit.getScheduler().runTaskLater(MLGRush.getInstance(), () -> {
                            if(playerManager.getRoundPoints() == MLGRush.getInstance().getPointsHandler().getMaxPoints()) {
                                MLGRush.getInstance().getEndingCountdown().start();
                                MLGRush.getInstance().getPlayerMoveScheduler().stopListening();
                                for (Player current : Bukkit.getOnlinePlayers()) {
                                    final PlayerManager currentManager = new PlayerManager(current);
                                    if(MLGRush.getInstance().getConfigManager().isCacheLoader())
                                        current.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY));
                                    else current.teleport(MLGRush.getInstance().getLocationHandler().getLocationByFile(String.valueOf(LocationType.LOBBY)));
                                    currentManager.sendTitle("§a§l" + player.getName(), "§7hat das Spiel gewonnen!");
                                    current.playSound(current.getLocation(), Sound.LEVEL_UP, 10, 10);
                                    currentManager.resetPlayer();
                                    MLGRush.getInstance().getInventoryManager().givePlayerLobbyItems(current);
                                    current.getInventory().setItem(0, new ItemStack(Material.AIR));
                                }
                            }
                        }, 2);
                    }
                }
            } else {
                event.setCancelled(true);
            }
        } else if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
            if(playerManager.getCurrentSetupBed() == null) {
                event.setCancelled(!playerManager.isInBuildMode());
            }
        }
    }

}
