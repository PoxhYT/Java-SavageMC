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
import de.pxav.mlgrush.utils.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {

        if (event.getItem() != null) {
            try {
                final PlayerManager playerManager = new PlayerManager(event.getPlayer());
                final Player player = playerManager.getPlayer();
                final MLGRush instance = MLGRush.getInstance();


                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(instance.getInventoryManager().getLOBBY_LEAVE())) {
                        event.setCancelled(true);
                        player.kickPlayer(MLGRush.getInstance().getPrefix() + "§7Du hast das Spiel verlassen.");
                    } else if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(instance.getInventoryManager().getLOBBY_TEAM_SELECTOR())) {
                        event.setCancelled(true);
                        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 3, 1);
                        instance.getInventoryManager().openTeamSelection(player);
                    } else if(event.getClickedBlock().getType() == Material.BED_BLOCK) {
                        event.setCancelled(true);
                        player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu darfst dich nicht in Betten legen.");
                    }
                }
            } catch (NullPointerException ignore){}
        }

    }

}
