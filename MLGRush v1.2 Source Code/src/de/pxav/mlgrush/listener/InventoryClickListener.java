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
import de.pxav.mlgrush.enums.TeamType;
import de.pxav.mlgrush.utils.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {

            final PlayerManager playerManager = new PlayerManager((Player) event.getWhoClicked());
            final Player player = playerManager.getPlayer();
            final MLGRush instance = MLGRush.getInstance();

            if (event.getClickedInventory().getName().equalsIgnoreCase(instance.getInventoryManager().getTEAM_SEL_TITLE())) {
                event.setCancelled(true);
                if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(instance.getInventoryManager().getTEAM_SEL_1())) {
                    player.playSound(player.getLocation(), Sound.CLICK, 3, 1);
                    if(instance.getTeamHandler().getPlayerTeam(player) != TeamType.TEAM_1) {
                        if(!instance.getTeamHandler().isTeamFull(TeamType.TEAM_1)) {
                            if(instance.getTeamHandler().getPlayerTeam(player) == TeamType.NONE)
                                instance.getTeamHandler().leaveTeam(player, TeamType.NONE);
                            if(instance.getTeamHandler().isInTeam(TeamType.TEAM_2, player))
                                instance.getTeamHandler().leaveTeam(player, TeamType.TEAM_2);
                            instance.getTeamHandler().joinTeam(player, TeamType.TEAM_1);
                            player.sendMessage(instance.getPrefix() + "§7Du hast das Team §a#1 §7betreten.");
                            player.closeInventory();
                            instance.getTabListHandler().setTabList(player);
                            MLGRush.getInstance().getScoreboardHandler().setScoreboard(player);
                        } else {
                            player.sendMessage(instance.getPrefix() + "§cDieses Team ist bereits voll.");
                            player.closeInventory();
                        }
                    } else {
                        player.sendMessage(instance.getPrefix() + "§cDu bist bereits in diesem Team.");
                        player.closeInventory();
                    }
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(instance.getInventoryManager().getTEAM_SEL_2())) {
                    player.playSound(player.getLocation(), Sound.CLICK, 3, 1);
                    if(instance.getTeamHandler().getPlayerTeam(player) != TeamType.TEAM_2) {
                        if(!instance.getTeamHandler().isTeamFull(TeamType.TEAM_2)) {
                            if(instance.getTeamHandler().getPlayerTeam(player) == TeamType.NONE)
                                instance.getTeamHandler().leaveTeam(player, TeamType.NONE);
                            if(instance.getTeamHandler().isInTeam(TeamType.TEAM_1, player))
                                instance.getTeamHandler().leaveTeam(player, TeamType.TEAM_1);
                            instance.getTeamHandler().joinTeam(player, TeamType.TEAM_2);
                            player.sendMessage(instance.getPrefix() + "§7Du hast das Team §a#2 §7betreten.");
                            player.closeInventory();
                            instance.getTabListHandler().setTabList(player);
                            MLGRush.getInstance().getScoreboardHandler().setScoreboard(player);
                        } else {
                            player.sendMessage(instance.getPrefix() + "§cDieses Team ist bereits voll.");
                            player.closeInventory();
                        }
                    } else {
                        player.sendMessage(instance.getPrefix() + "§cDu bist bereits in diesem Team.");
                        player.closeInventory();
                    }
                    player.playSound(player.getLocation(), Sound.CLICK, 3, 1);
                }
            }

        }
    }

}
