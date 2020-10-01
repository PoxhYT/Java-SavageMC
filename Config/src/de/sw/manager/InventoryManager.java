package de.sw.manager;

import de.sw.api.ItemBuilderAPI;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    static File file = new File("plugins/Config", "config.yml");

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public void LobbyInventory(Player player) {

        player.getInventory().setItem(0, new ItemBuilderAPI(Material.BED).setDisplayName("§eTeams").build());
        player.getInventory().setItem(3, new ItemBuilderAPI(Material.CHEST).setDisplayName("§6Kits").build());
        player.getInventory().setItem(5, new ItemBuilderAPI(Material.BED).setDisplayName("§5Achievements").build());
        player.getInventory().setItem(8, new ItemBuilderAPI(Material.MAGMA_CREAM).setDisplayName("§cVerlassen").build());
    }

    public void openTeamInventory(Player player) {

        String size = yamlConfiguration.getString("Size");
        Inventory inventory = Bukkit.createInventory(null, 9, "§cTeams");
        if(size.equals("8x1")) {
            Integer teams = yamlConfiguration.getInt("Teams");
            for (int i = 0; i < teams; i++)
            inventory.setItem(i, new ItemBuilderAPI(Material.WOOL).setDisplayName("§7Team #§e" + teams).build());
            player.openInventory(inventory);
        }
    }

    public void openSetupInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 27, "§bSetup");
        for (int i = 0; i < 27; i++)
            inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE,(short)7).setDisplayName("§1 ").build());
        inventory.setItem(10, new ItemBuilderAPI(Material.SLIME_BALL).setDisplayName("§eLobby").setLore("§8➥ §7Setze die §eLobby§7.").build());
        inventory.setItem(12, new ItemBuilderAPI(Material.BARRIER).setDisplayName("§cZuschauer").setLore("§8➥ §7Setze den §eSpawn §7für die §cZuschauer§7.").build());
        inventory.setItem(14, new ItemBuilderAPI(Material.BED).setDisplayName("§bTeam§7-§bVillager").setLore("§8➥ §7Setze den §bTeam§7-§bVillager§7.").build());
        inventory.setItem(16, new ItemBuilderAPI(Material.PAPER).setDisplayName("§5Spawns").setLore("§8➥ §7Setze die §5Spawns§7.").build());
        player.openInventory(inventory);
    }

    public void openSpawnInventory(Player player) {
        String size = yamlConfiguration.getString("Size");
        if(size.equals("8x1")) {
            Inventory inventory = Bukkit.createInventory(null, 36, "§5Spawns");
            for (int i = 0; i < 36; i++)
                inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE,(short)7).setDisplayName("§1 ").build());
            inventory.setItem(10, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §71").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §71§7.").build());
            inventory.setItem(12, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §72").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §72§7.").build());
            inventory.setItem(14, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §73").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §73§7.").build());
            inventory.setItem(16, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §74").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §74§7.").build());
            inventory.setItem(19, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §75").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §75§7.").build());
            inventory.setItem(21, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §76").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §76§7.").build());
            inventory.setItem(23, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §77").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §77§7.").build());
            inventory.setItem(25, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §78").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §78§7.").build());
            player.openInventory(inventory);
        }
        if(size.equals("8x2")) {
            Inventory inventory = Bukkit.createInventory(null, 36, "§5Spawns");
            for (int i = 0; i < 36; i++)
                inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE, (short) 7).setDisplayName("§1 ").build());
            inventory.setItem(10, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §71").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §71§7.").build());
            inventory.setItem(12, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §72").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §72§7.").build());
            inventory.setItem(14, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §73").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §73§7.").build());
            inventory.setItem(16, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam §8: §74").setLore("§8➥ §7Setze den §eSpawn §7für §eTeam §8: §74§7.").build());
            player.openInventory(inventory);
        }
    }

    public void getKitInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§eKits");
        for (int i = 0; i < 27; i++)
            inventory.setItem(i, new ItemBuilderAPI(Material.STAINED_GLASS_PANE, (short) 7).setDisplayName("§1 ").build());
        inventory.setItem(0, new ItemBuilderAPI(Material.IRON_PICKAXE).setDisplayName("§8» §eStarter §8[§aGekauft§8]").setLore("§bAusrüstung", "§71x Eisenschwert", "§71x Eisenspitzhacke", "§71x Eisenaxt").build());
        inventory.setItem(1, new ItemBuilderAPI(Material.BRICK).setDisplayName("§8» §eMaurer").setLore("§bAusrüstung", "§764x Ziegelsteine", "§71x Goldhelm mit Schutz IV und Haltbarkeit III", "§71x Goldspitzhacke mit Effizienz III").build());
        inventory.setItem(2, new ItemBuilderAPI(Material.ENCHANTMENT_TABLE).setDisplayName("§8» §eZauberer").setLore("§bAusrüstung", "§732x Erfahrungsfläschchen", "§71x Zaubertisch").build());
        player.openInventory(inventory);
    }
}
