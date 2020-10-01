package de.mlgrush.listener;

import de.mlgrush.enums.TeamType;
import de.mlgrush.main.Main;
import de.mlgrush.utils.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getWhoClicked() instanceof Player) {
            PlayerManager playerManager = new PlayerManager((Player)event.getWhoClicked());
            Player player = playerManager.getPlayer();
            Main instance = Main.getInstance();
            if (event.getClickedInventory().getName().equalsIgnoreCase("§eTeamauswahl")) {
                event.setCancelled(true);
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Team: §9BLAU")) {
                    player.setDisplayName("§9" + player.getName());
                    player.playSound(player.getLocation(), Sound.CLICK, 3.0F, 1.0F);
                    if (instance.getTeamHandler().getPlayerTeam(player) != TeamType.TEAM_1) {
                        if (!instance.getTeamHandler().isTeamFull(TeamType.TEAM_1)) {
                            if (instance.getTeamHandler().getPlayerTeam(player) == TeamType.NONE)
                                instance.getTeamHandler().leaveTeam(player, TeamType.NONE);
                            if (instance.getTeamHandler().isInTeam(TeamType.TEAM_2, player))
                                instance.getTeamHandler().leaveTeam(player, TeamType.TEAM_2);
                            instance.getTeamHandler().joinTeam(player, TeamType.TEAM_1);
                            player.sendMessage(Main.prefix + "Du hast das Team: §9Blau §7betreten. ");
                            player.closeInventory();
                            instance.getTabListHandler().setTabList(player);
                            Main.getInstance().getScoreboardHandler().setScoreboard(player);
                        } else {
                            player.sendMessage(Main.prefix + "§cDieses Team ist bereits voll.");
                            player.closeInventory();
                        }
                    } else {
                        player.sendMessage(Main.prefix + "Du bist bereits in diesem Team.");
                        player.closeInventory();
                    }
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Team: §cROT")) {
                    player.playSound(player.getLocation(), Sound.CLICK, 3.0F, 1.0F);
                    if (instance.getTeamHandler().getPlayerTeam(player) != TeamType.TEAM_2) {
                        if (!instance.getTeamHandler().isTeamFull(TeamType.TEAM_2)) {
                            if (instance.getTeamHandler().getPlayerTeam(player) == TeamType.NONE)
                                instance.getTeamHandler().leaveTeam(player, TeamType.NONE);
                            if (instance.getTeamHandler().isInTeam(TeamType.TEAM_1, player))
                                instance.getTeamHandler().leaveTeam(player, TeamType.TEAM_1);
                            instance.getTeamHandler().joinTeam(player, TeamType.TEAM_2);
                            player.sendMessage(Main.prefix + "hast das Team: §cRot §7betreten. ");
                            player.closeInventory();
                            instance.getTabListHandler().setTabList(player);
                            Main.getInstance().getScoreboardHandler().setScoreboard(player);
                        } else {
                            player.sendMessage(Main.prefix + "Dieses Team ist bereits voll.");
                            player.closeInventory();
                        }
                    } else {
                        player.sendMessage(Main.prefix + "Du bist bereits in diesem Team.");
                        player.closeInventory();
                    }
                    player.playSound(player.getLocation(), Sound.CLICK, 3.0F, 1.0F);
                }
            }
        }
    }
}



