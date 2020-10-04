package de.sw.listener;

import de.sw.main.Main;
import de.sw.manager.ItemBuilderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class PlayerInteractListener implements Listener {

    public static int cps;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getPlayer() == null)
            return;
        if (e.getItem() != null)
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                if(e.getItem().getType() == Material.MUSHROOM_SOUP)
                    heal(e.getPlayer());
    }



    public void openTeamInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 9, "§8• §eTeams");
        player.openInventory(inventory);
    }

    private void heal(Player player) {
        int health = (int)player.getHealth();
        if (health == 20)
            return;
        if (health > 12) {
            player.setHealth((health + 1));
            player.getInventory().getItemInHand().setType(Material.BOWL);
            player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
            return;
        }
        player.setHealth((health + 8));
        player.getInventory().getItemInHand().setType(Material.BOWL);
        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
    }

}