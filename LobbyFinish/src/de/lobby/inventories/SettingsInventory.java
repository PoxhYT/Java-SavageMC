package de.lobby.inventories;

import de.lobby.utils.ItemAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class SettingsInventory {

    public static Inventory settingsInventory = Bukkit.createInventory(null, 27, "§8• §r§9Einstellungen §8•");

    public static void settingsFill() {
        for (int i = 0; i < settingsInventory.getSize(); i++)
            settingsInventory.setItem(i, new ItemAPI(Material.STAINED_GLASS_PANE, (short)15).setName("§r").build());

        settingsInventory.setItem(13, new ItemAPI(Material.NETHER_STAR).setName("§8• §r§bServer Settings §8•").setLore("§r§7Verbinde §r§7dich mit verschiedene §r§bServer§7.").build());
        settingsInventory.setItem(11, new ItemAPI(Material.FEATHER).setName("§8• §r§5Lobby Features §8•").setLore("§r§7Klicke, um verschiedene §r§5Features §r§7zu benutzen..").build());
    }
}

