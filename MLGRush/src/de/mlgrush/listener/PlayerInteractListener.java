package de.mlgrush.listener;

import de.mlgrush.main.Main;
import de.mlgrush.utils.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            PlayerManager playerManager = new PlayerManager(event.getPlayer());
            Player player = playerManager.getPlayer();
            Main instance = Main.getInstance();
            if(event.getItem().getType() == Material.MAGMA_CREAM) {
                player.kickPlayer(Main.prefix + "§cDu hast das Spiel verlassen!");
            }else if(event.getItem().getType() == Material.BED) {
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                Main.getInstance().getInventoryManager().openTeamSelector(player);
            } else if (event.getClickedBlock().getType() == Material.BED_BLOCK) {
                event.setCancelled(true);
            }
        }catch (NullPointerException e) {}
    }
}
