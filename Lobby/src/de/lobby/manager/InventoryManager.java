package de.lobby.manager;

import de.lobby.api.ItemBuilderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryManager {

    public void openNavigatorInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 45, "§eNavigator");
        for(int i = 0; i < 45; i++)
        inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE).setDisplayName("§1 ").build());
        inventory.setItem(12, new ItemBuilderAPI(Material.GRASS).setDisplayName("§bSkyWars").build());
        inventory.setItem(14, new ItemBuilderAPI(Material.BED).setDisplayName("§cBedWars").build());
        inventory.setItem(20, new ItemBuilderAPI(Material.MUSHROOM_SOUP).setDisplayName("§9Soup-Training").build());
        inventory.setItem(22, new ItemBuilderAPI(Material.NETHER_STAR).setDisplayName("§eSpawn").build());
        inventory.setItem(24, new ItemBuilderAPI(Material.STICK).setDisplayName("§2MLG-RUSH").build());
        inventory.setItem(32, new ItemBuilderAPI(Material.GOLDEN_APPLE).setDisplayName("§eUHC").build());
        inventory.setItem(30, new ItemBuilderAPI(Material.GOLDEN_CARROT).setDisplayName("§6SPEED-UHC").build());
        player.openInventory(inventory);
    }

    public void setLobbyInventory(Player player) {
        player.getInventory().setItem(0, new ItemBuilderAPI(Material.COMPASS).setDisplayName("§eNavigator").build());
    }
}
