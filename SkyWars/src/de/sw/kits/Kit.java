package de.sw.kits;

import de.sw.manager.ItemBuilderAPI;
import de.sw.manager.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Kit {

    static Inventory inventory = Bukkit.createInventory(null, 36, "§eKits");

    public void openKitInventory(Player player) {

        KitManager standard = new KitManager("Standard §8[§aGekauft§8]", "", Material.IRON_PICKAXE, 0);
        String[] description = {"§7Du startest mit §e1 Eisenschwert§7,", "§e1 Eisenspitzhacke§7, §e1 Eisenaxt"};
        inventory.setItem(0, new ItemBuilderAPI(standard.getKitIcon()).setDisplayName(standard.getKitName()).setLore(description).build());

        KitManager brick = new KitManager("Maurer", "", Material.IRON_PICKAXE, 0);
        String[] description2 = {"§7Du startest mit §e1 Eisenschwert§7,", "§e1 Eisenspitzhacke§7, §e1 Eisenaxt"};
        inventory.setItem(1, new ItemBuilderAPI(brick.getKitIcon()).setDisplayName(brick.getKitName() + " §8[§ePreis§7: §c" + brick.getKitPrice() + "§8]").setLore(description2).build());

        player.openInventory(inventory);

    }
}
