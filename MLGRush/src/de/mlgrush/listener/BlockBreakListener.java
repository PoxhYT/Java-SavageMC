package de.mlgrush.listener;

import de.mlgrush.enums.GameState;
import de.mlgrush.enums.LocationType;
import de.mlgrush.enums.TeamType;
import de.mlgrush.gamestates.GameStateHandler;
import de.mlgrush.main.Main;
import de.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        PlayerManager playerManager = new PlayerManager(event.getPlayer());
        Player player = playerManager.getPlayer();
        Main instance = Main.getInstance();
        if (playerManager.getCurrentSetupBed() == LocationType.BED_TOP_1) {
            event.setCancelled(true);
            instance.getLocationHandler().addLocation(LocationType.BED_TOP_1.toString(), event.getBlock().getLocation());
            playerManager.setCurrentSetupBed(LocationType.BED_BOTTOM_1);
            player.sendMessage(Main.prefix + "Schlage nun den unteren Teil des Bettes von Team: §9Blau§7.");
        } else if (playerManager.getCurrentSetupBed() == LocationType.BED_BOTTOM_1) {
            event.setCancelled(true);
            instance.getLocationHandler().addLocation(LocationType.BED_BOTTOM_1.toString(), event.getBlock().getLocation());
            playerManager.setCurrentSetupBed(null);
            player.sendMessage(Main.prefix + "Einrichtung des Bettes von TEam: §9Blau §7abgeschlossen.");
        } else if (playerManager.getCurrentSetupBed() == LocationType.BED_TOP_2) {
            event.setCancelled(true);
            instance.getLocationHandler().addLocation(LocationType.BED_TOP_2.toString(), event.getBlock().getLocation());
            playerManager.setCurrentSetupBed(LocationType.BED_BOTTOM_2);
            player.sendMessage(Main.prefix + "Schlage nun den unteren Teil des Bettes von Team: §cRot§7.");
        } else if (playerManager.getCurrentSetupBed() == LocationType.BED_BOTTOM_2) {
            event.setCancelled(true);
            instance.getLocationHandler().addLocation(LocationType.BED_BOTTOM_2.toString(), event.getBlock().getLocation());
            playerManager.setCurrentSetupBed(null);
            player.sendMessage(Main.prefix + "Einrichtung des Bettes von TEam: §9Blau §7abgeschlossen.");
        }

        if (GameStateHandler.getGameState() == GameState.INGAME) {
            if (event.getBlock().getType() == Material.SANDSTONE && instance.getMapResetHandler().canBreak(event.getBlock().getLocation()) && Main.getInstance().getRegionManager().isInRegion(event.getBlock().getLocation())) {
                event.setCancelled(true);
                event.getBlock().getLocation().getWorld().getBlockAt(event.getBlock().getLocation()).setType(Material.AIR);
            } else if (event.getBlock().getType() == Material.BED_BLOCK) {
                if (event.getBlock().getLocation().equals(instance.getLocationHandler().getLocationByFile(LocationType.BED_TOP_1)) || event
                        .getBlock().getLocation().equals(instance.getLocationHandler().getLocationByFile(LocationType.BED_BOTTOM_1))) {
                    if (instance.getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_1) {
                        event.setCancelled(true);
                        player.sendMessage(Main.prefix + "Du darfst dein eigenes Bett nicht abbauen.");
                    } else if (instance.getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_2) {
                        event.setCancelled(true);
                        playerManager.addRoundPoint();
                        playerManager.sendTitle("§a§l+", "Du hast ein Bett abgebaut.");
                        for (Player current : Bukkit.getOnlinePlayers()) {
                            PlayerManager currentManager = new PlayerManager(current);
                            player.playSound(player.getLocation(), Sound.ANVIL_USE, 3.0F, 1.0F);
                            currentManager.teleportToTeamIsland();
                            Main.getInstance().getInventoryManager().giveIngameItems(current);
                            if (!current.getName().equalsIgnoreCase(player.getName()))
                                currentManager.sendTitle("§c§lACHTUNG", "Dein Bett wurde abgebaut");
                        }
                        Main.getInstance().getMapResetHandler().resetMap(false);
                        Bukkit.getScheduler().runTaskLater((Plugin)Main.getInstance(), () -> {
                            if (playerManager.getRoundPoints() == Main.getInstance().getPointsHandler().getMaxPoints()) {
                                Main.getInstance().getPlayerMoveScheduler().stopListening();
                                Main.getInstance().getEndingCountdown().start();
                                for (Player current : Bukkit.getOnlinePlayers()) {
                                    PlayerManager currentManager = new PlayerManager(current);
                                    if (Main.getInstance().getConfigManager().isCacheLoader()) {
                                        current.teleport(Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY));
                                    } else {
                                        current.teleport(Main.getInstance().getLocationHandler().getLocationByFile(String.valueOf(LocationType.LOBBY)));
                                    }
                                    currentManager.sendTitle("§a§l"+ player.getName(), "§7hat das Spiel gewonnen!");
                                    current.playSound(current.getLocation(), Sound.LEVEL_UP, 10.0F, 10.0F);
                                    currentManager.resetPlayer();
                                    Main.getInstance().getInventoryManager().giveLobbyItems(current);
                                    current.getInventory().setItem(0, new ItemStack(Material.AIR));
                                }
                            }
                        }, 2L);
                    }
                } else if (event.getBlock().getLocation().equals(instance.getLocationHandler().getLocationByFile(String.valueOf(LocationType.BED_TOP_2))) || event
                        .getBlock().getLocation().equals(instance.getLocationHandler().getLocationByFile(String.valueOf(LocationType.BED_BOTTOM_2)))) {
                    if (instance.getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_2) {
                        event.setCancelled(true);
                        player.sendMessage(Main.prefix + "Du darfst dein eigenes Bett nicht abbauen.");
                    } else if (instance.getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_1) {
                        event.setCancelled(true);
                        playerManager.addRoundPoint();
                        playerManager.sendTitle("§a§l+", "Du hast ein Bett abgebaut. ");
                        for (Player current : Bukkit.getOnlinePlayers()) {
                            PlayerManager currentManager = new PlayerManager(current);
                            player.playSound(player.getLocation(), Sound.ANVIL_USE, 3.0F, 1.0F);
                            currentManager.teleportToTeamIsland();
                            Main.getInstance().getInventoryManager().giveIngameItems(current);
                            if (!current.getName().equalsIgnoreCase(player.getName()))
                                currentManager.sendTitle("§c§lACHTUNG", "Dein Bett wurde abgebaut ");
                        }
                        Main.getInstance().getMapResetHandler().resetMap(false);
                        Bukkit.getScheduler().runTaskLater((Plugin)Main.getInstance(), () -> {
                            if (playerManager.getRoundPoints() == Main.getInstance().getPointsHandler().getMaxPoints()) {
                                Main.getInstance().getEndingCountdown().start();
                                Main.getInstance().getPlayerMoveScheduler().stopListening();
                                for (Player current : Bukkit.getOnlinePlayers()) {
                                    PlayerManager currentManager = new PlayerManager(current);
                                    if (Main.getInstance().getConfigManager().isCacheLoader()) {
                                        current.teleport(Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY));
                                    } else {
                                        current.teleport(Main.getInstance().getLocationHandler().getLocationByFile(String.valueOf(LocationType.LOBBY)));
                                    }
                                    currentManager.sendTitle("" + player.getName(), "das Spiel gewonnen!");
                                    current.playSound(current.getLocation(), Sound.LEVEL_UP, 10.0F, 10.0F);
                                    currentManager.resetPlayer();
                                    Main.getInstance().getInventoryManager().giveLobbyItems(current);
                                    current.getInventory().setItem(0, new ItemStack(Material.AIR));
                                }
                            }
                        },2L);
                    }
                }
            } else {
                event.setCancelled(true);
            }
        } else if ((GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) &&
                playerManager.getCurrentSetupBed() == null) {
            event.setCancelled(!playerManager.isInBuildMode());
        }
    }
}




