package de.sw.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class InventoryManager {

    static File file = new File("plugins/SkyWars", "Config.yml");

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public void setLobbyInventory(Player player) {
        KitManager kitManager = new KitManager("§eStandard", "§eYARRAK", Material.BAKED_POTATO, 2);
        player.getInventory().setItem(2, new ItemBuilderAPI(kitManager.getKitIcon()).setDisplayName(kitManager.getKitName() + kitManager.getKitPrice()).setLore(kitManager.getKitDescription()).build());
        player.getInventory().setItem(0, new ItemBuilderAPI(Material.BED).setDisplayName("§8» §bTeams").build());
        player.getInventory().setItem(3, new ItemBuilderAPI(Material.CHEST).setDisplayName("§8» §eKits").build());
        player.getInventory().setItem(5, new ItemBuilderAPI(Material.NETHER_STAR).setDisplayName("§8» §dAchievements").build());
        player.getInventory().setItem(8, new ItemBuilderAPI(Material.MAGMA_CREAM).setDisplayName("§8» §cVerlassen").build());
    }

    public void setTeamInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§8» §bTeams");
        String gameSize = yamlConfiguration.getString("gameSize");
        if(gameSize.equals("8x1")) {
            inventory.setItem(0, new ItemBuilderAPI(Material.WOOL).setDisplayName("§7Team #§e1").build());
            inventory.setItem(1, new ItemBuilderAPI(Material.WOOL).setDisplayName("§7Team #§e2").build());
            inventory.setItem(2, new ItemBuilderAPI(Material.WOOL).setDisplayName("§7Team #§e3").build());
            inventory.setItem(3, new ItemBuilderAPI(Material.WOOL).setDisplayName("§7Team #§e4").build());
            inventory.setItem(4, new ItemBuilderAPI(Material.WOOL).setDisplayName("§7Team #§e5").build());
            inventory.setItem(5, new ItemBuilderAPI(Material.WOOL).setDisplayName("§7Team #§e6").build());
            inventory.setItem(6, new ItemBuilderAPI(Material.WOOL).setDisplayName("§7Team #§e7").build());
            inventory.setItem(7, new ItemBuilderAPI(Material.WOOL).setDisplayName("§7Team #§e8").build());
            player.openInventory(inventory);
        }
    }

    public void openKitInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, "§eKits");

        //----------------------------------------------------------------------------------------------------------------//
        KitManager standard = new KitManager("Standard §8[§aGekauft§8]", "", Material.IRON_PICKAXE, 0);
        String[] description = {"§7Du startest mit §e1 Eisenschwert§7,", "§e1 Eisenspitzhacke§7, §e1 Eisenaxt"};
        inventory.setItem(0, new ItemBuilderAPI(standard.getKitIcon()).setDisplayName(standard.getKitName()).setLore(description).build());

        KitManager brick = new KitManager("Maurer", "", Material.BRICK, 10000);
        String[] description2 = {"§8✘ §eAusrüstung", "§8✘ §764 Ziegelsteine", "§8✘ §7Goldhelm mit Schutz III", "§8✘ §7Goldspitzhacke mit Effizienz III und Haltbarkeit II"};
        inventory.setItem(2, new ItemBuilderAPI(brick.getKitIcon()).setDisplayName(brick.getKitName() + " §8[§ePreis§7: §c" + brick.getKitPrice() + "§8]").setLore(description2).build());

        KitManager healer = new KitManager("Healer", "", Material.GHAST_TEAR, 5000);
        String[] description3 = {"§8✘ §eAusrüstung", "§8✘ §73x Heilungstränke mit Heilung II", "§8✘ §71 Regenerationstrank für 1:30 Minuten"};
        inventory.setItem(1, new ItemBuilderAPI(healer.getKitIcon()).setDisplayName(healer.getKitName() + " §8[§ePreis§7: §c" + healer.getKitPrice() + "§8]").setLore(description3).build());

        player.openInventory(inventory);

    }
}
