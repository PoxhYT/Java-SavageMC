package de.sw.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    static File file = new File("plugins/SkyWars", "Config.yml");

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public void setLobbyInventory(Player player) {
        KitManager kitManager = new KitManager("§eStandard", new String[] {"§eYARRAK"}, Material.BAKED_POTATO, 2);
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
            for (int i = 0; i < 8; i++)
            {
                inventory.setItem(i, new ItemBuilderAPI(Material.WOOL).setDisplayName("§7Team #§e" + i).build());
            }
            player.openInventory(inventory);
        }
    }

    public void openKitInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, "§eKits");

        KitManager[] kits = getKits();

        for (int i = 0; i < kits.length; i++)
        {
            inventory.setItem(i, new ItemBuilderAPI(kits[i].getKitIcon()).setDisplayName(kits[i].getKitName()).setLore(kits[i].getKitDescription()).build());
        }

        player.openInventory(inventory);
    }

    private KitManager[] getKits() {
        List<KitManager> arr = new ArrayList<>();

         arr.add(new KitManager("Standard §8[§aGekauft§8]",
                 new String[]
                            {
                            "§7Du startest mit §e1 Eisenschwert§7,",
                            "§e1 Eisenspitzhacke§7, §e1 Eisenaxt"
                            },
                 Material.IRON_PICKAXE, 0)
         );

        arr.add(new KitManager("Maurer",
                    new String[] {"§8✘ §eAusrüstung",
                            "§8✘ §764 Ziegelsteine",
                            "§8✘ §7Goldhelm mit Schutz III",
                            "§8✘ §7Goldspitzhacke mit Effizienz III und Haltbarkeit II"},
                    Material.BRICK, 10000)
        );

        arr.add(new KitManager("Healer",
                new String[] {"§8✘ §eAusrüstung",
                        "§8✘ §73x Heilungstränke mit Heilung II",
                        "§8✘ §71 Regenerationstrank für 1:30 Minuten"},
                Material.GHAST_TEAR, 5000)
        );

        return (KitManager[]) arr.toArray();
    }
}
