package de.lobby.inventories;

import de.lobby.utils.ItemAPI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class NavigatorInventory {

    public static Inventory navigatorInventory = Bukkit.createInventory(null, 45, "§8• §r§bNavigator §8•");

    public static void navigatorFill() {
        for (int i = 0; i < navigatorInventory.getSize(); i++)
            navigatorInventory.setItem(i, new ItemAPI(Material.STAINED_GLASS_PANE, (short)15).setName("§r").build());

        navigatorInventory.setItem(22, new ItemAPI(Material.NETHER_STAR).setName("§eSpawn").setLore("§r§eTeleportiere §r§7dich, zum §r§eSpawn§7.").build());
        navigatorInventory.setItem(12, new ItemAPI(Material.GRASS).setName("§bMain").setLore("§r§eTeleportiere §r§7dich, zu §r§bMain§7.").build());
        navigatorInventory.setItem(14, new ItemAPI(Material.BED).setName("§cBedWars").setLore("§r§eTeleportiere §r§7dich, zu §r§cBedWars§7.").build());
        navigatorInventory.setItem(24, new ItemAPI(Material.STICK).setName("§2MLG§7-§2RUSH").setLore("§r§eTeleportiere §r§7dich, zu §r§2MLG§7-§2RUSH§7.").build());
        navigatorInventory.setItem(32, new ItemAPI(Material.GOLDEN_APPLE).setName("§eSpeed§7-§eUHC").setLore("§r§eTeleportiere §r§7dich, zu §r§eSpeed§7-§eUHC§7.").build());
        navigatorInventory.setItem(30, new ItemAPI(Material.IRON_AXE).setName("§9CityBuild").setLore("§r§eTeleportiere §r§7dich, zu §r§9CityBuild§7.").build());
        navigatorInventory.setItem(20, new ItemAPI(Material.DIAMOND_PICKAXE).setName("§aFactions").setLore("§r§eTeleportiere §r§7dich, zu §r§aFactions§7.").build());
    }
}
