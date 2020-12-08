package com.poxh.listener;

import com.poxh.api.LocationAPI;
import com.poxh.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInteractListener implements Listener {

    private void openInventory(Player player) {}

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eNavigator")) {
                Main.inventoryManager.openNavigator(player);
                player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
            }
        }catch (NullPointerException e){}
    }
}
