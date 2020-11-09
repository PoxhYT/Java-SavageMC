package de.mlgrush.inventory;

import de.mlgrush.api.ItemBuilderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class InventoryManager {

    public static void setLobbyInventory(Player player) {
        player.getInventory().setItem(0, new ItemBuilderAPI(Material.BED).setDisplayName("§eTeamauswahl").build());
        player.getInventory().setItem(4, new ItemBuilderAPI(Material.CHEST).setDisplayName("§5Cosmetics").build());
        player.getInventory().setItem(8, new ItemBuilderAPI(Material.MAGMA_CREAM).setDisplayName("§cLeave").build());
    }

    public static void openTeamSelector(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§eTeamauswahl");
        ArrayList<String> lore1 = new ArrayList<>();
    }
}
