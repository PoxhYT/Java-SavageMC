package com.poxh.listener;

import com.poxh.main.Main;
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
            Player player = event.getPlayer();
            if(event.getItem().getType() == Material.COMPASS) {
                Main.inventoryManager.NaviInventory(player);
                player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
            }
        }catch (NullPointerException e){}
    }
}
