package de.cb.poxh.manager;

import de.cb.poxh.api.ItemBuilderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class InventoryManager {

    public void openMenu(Player player) {
        Material blackGlass = Material.BLACK_STAINED_GLASS_PANE;
        Inventory inventory = Bukkit.createInventory(null , 45, "§bTeleporter");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilderAPI(blackGlass).setDisplayName("§1 ").build());
        }
        inventory.setItem(21, new ItemBuilderAPI(Material.WITHER_SKELETON_SKULL).setDisplayName("§7" + player.getName() + "'s §ePlot").setLore("§7➥§eLinksklick zum teleportieren!").build());
        inventory.setItem(23, new ItemBuilderAPI(Material.GRASS).setDisplayName("§aFarming Welt").setLore("§7➥§eLinks zum teleportieren!").build());
        player.openInventory(inventory);
    }

    public void openShop(Player player) {
        Material blackGlass = Material.BLACK_STAINED_GLASS_PANE;
        Inventory inventory = Bukkit.createInventory(null , 27, "§6§lItem§7§l-§6§lShop");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilderAPI(blackGlass).setDisplayName("§1 ").build());
        }
        inventory.setItem(11, new ItemBuilderAPI(Material.GRASS_BLOCK).setDisplayName("§b§lBloecke").setLore("§7➥ Linksklick um die §b§lKategorie§7: §b§lBloecke §7zu oeffnen").build());
        inventory.setItem(13, new ItemBuilderAPI(Material.COOKED_PORKCHOP).setDisplayName("§b§lEssen").setLore("§7➥ Linksklick um die §b§lKategorie§7: §b§lEssen §7zu oeffnen").build());
        inventory.setItem(15, new ItemBuilderAPI(Material.VILLAGER_SPAWN_EGG).setDisplayName("§b§lSpawn Egg").setLore("§7➥ Linksklick um die §b§lKategorie§7: §b§lSpawn Egg §7zu oeffnen").build());
        player.openInventory(inventory);
    }

    public void openBlocks(Player player) {
        Material blackGlass = Material.BLACK_STAINED_GLASS_PANE;
        Inventory inventory = Bukkit.createInventory(null , 27, "§6§lItem§7§l-§6§lShop");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilderAPI(blackGlass).setDisplayName("§1 ").build());
        }
        inventory.setItem(0, new ItemBuilderAPI(Material.GRASS_BLOCK).setLore("§7➥ §e§lUm das Item zu kaufen").build());
        inventory.setItem(1, new ItemBuilderAPI(Material.DIRT).setLore("§7➥ §e§lUm das Item zu kaufen").build());
        inventory.setItem(2, new ItemBuilderAPI(Material.COARSE_DIRT).setLore("§7➥ §e§lUm das Item zu kaufen").build());
        inventory.setItem(3, new ItemBuilderAPI(Material.SAND).setLore("§7➥ §e§lUm das Item zu kaufen").build());
        inventory.setItem(4, new ItemBuilderAPI(Material.SANDSTONE).setLore("§7➥ §e§lUm das Item zu kaufen").build());
        inventory.setItem(5, new ItemBuilderAPI(Material.CHISELED_SANDSTONE).setLore("§7➥ §e§lUm das Item zu kaufen").build());

    }
}
