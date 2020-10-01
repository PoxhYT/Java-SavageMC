package de.sw.listener;

import de.sw.main.Main;
import de.sw.manager.KitManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInventoryClickListener implements Listener {

    public KitManager kitManager;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        try {
            if (event.getClickedInventory().getName().equals("§eKits")) {
                if(event.getCurrentItem().getType() == Material.IRON_PICKAXE) {
                    player.sendMessage(Main.prefix + kitManager.getKitName());
                }
            }
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
