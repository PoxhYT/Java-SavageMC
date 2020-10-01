package de.lobby.inventories;

import de.lobby.utils.ItemAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class LobbySwitcherInventory {

    public static Inventory lobbySwitchInventory = Bukkit.createInventory(null, 27, "§8• §r§bLobbySwitcher §8•");

    public static void lobbySwitchFill() {
        for (int i = 0; i < lobbySwitchInventory.getSize(); i++)
            lobbySwitchInventory.setItem(i, new ItemAPI(Material.STAINED_GLASS_PANE, (short)15).setName("§r").build());

        lobbySwitchInventory.setItem(10, new ItemAPI(Material.NETHER_STAR).setName("§8• §r§bLobby§7-§b1 §8•").build());
        lobbySwitchInventory.setItem(12, new ItemAPI(Material.GLOWSTONE_DUST).setName("§8• §r§ePremiumLobby§7-§e1 §8•").build());
        lobbySwitchInventory.setItem(14, new ItemAPI(Material.REDSTONE).setName("§8• §r§5TeamLobby§7-§r§51 §8•").build());
        lobbySwitchInventory.setItem(16, new ItemAPI(Material.REDSTONE_COMPARATOR).setName("§8• §r§5ServerSettings §8•").setLore("§r§7Ändere deine §r§5Server§7-Einstellungen§7.").build());
    }
}

