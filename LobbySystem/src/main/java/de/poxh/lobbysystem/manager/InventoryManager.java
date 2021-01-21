package de.poxh.lobby.manager;

import de.poxh.lobby.api.ItemBuilderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryManager {

    public static void setLobbyInventory(Player player) {
        player.getInventory().setItem(0, new ItemBuilderAPI(Material.COMPASS).setDisplayName("§bNavigator §8❘ §7Rechtsklick").setLore("§8➥ §eRechtsklick zum öffnen").build());
        player.getInventory().setItem(4, new ItemBuilderAPI(Material.CHEST).setDisplayName("§6Gadgets §8❘ §7Rechtsklick").setLore("§8➥ §eRechtsklick zum öffnen").build());
        player.getInventory().setItem(8, ItemBuilderAPI.getHead(player, "§5Freunde §8❘ §7Rechtsklick", "§8➥ §eRechtsklick zum öffnen"));
    }

    public static void openNavigatorInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 45, "§bNavigator");
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

    public static void openRewardInv(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§eTägliche Belohnungen");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE, (short)7).setDisplayName("§1").build());
        }
    }
}
