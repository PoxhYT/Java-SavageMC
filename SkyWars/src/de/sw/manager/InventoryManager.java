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
        player.getInventory().setItem(0, new ItemBuilderAPI(Material.BED).setDisplayName("§8» §bTeams").build());
        player.getInventory().setItem(3, new ItemBuilderAPI(Material.CHEST).setDisplayName("§8» §eKits").build());
        player.getInventory().setItem(5, new ItemBuilderAPI(Material.NETHER_STAR).setDisplayName("§8» §dAchievements").build());
        player.getInventory().setItem(8, new ItemBuilderAPI(Material.MAGMA_CREAM).setDisplayName("§8» §cVerlassen").build());
    }

    public void setTeamInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 9, "§8• §eTeams");
        TeamManager[] teams = getTeams();
        for (int i = 0; i < teams.length; i++)
            inventory.setItem(i, new ItemBuilderAPI(teams[i].getMaterial()).setDisplayName(teams[i].getTeamName()).build());
        player.openInventory(inventory);
    }

    private TeamManager[] getTeams() {
        List<TeamManager> teamManagers = new ArrayList<>();

        teamManagers.add(new TeamManager("§7Team1", Material.WOOL, "§eTeam1"));
        teamManagers.add(new TeamManager("§7Team2", Material.WOOL, "§eTeam2"));

        return (TeamManager[]) teamManagers.toArray();
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
        KitManager[] arr = new KitManager[3];

         arr[0] = (new KitManager("Standard §8[§aGekauft§8]",
                 new String[]
                            {
                            "§7Du startest mit §e1 Eisenschwert§7,",
                            "§e1 Eisenspitzhacke§7, §e1 Eisenaxt"
                            },
                 Material.IRON_PICKAXE, 0)
         );

        arr[1] = (new KitManager("Maurer",
                    new String[] {"§8✘ §eAusrüstung",
                            "§8✘ §764 Ziegelsteine",
                            "§8✘ §7Goldhelm mit Schutz III",
                            "§8✘ §7Goldspitzhacke mit Effizienz III und Haltbarkeit II"},
                    Material.BRICK, 10000)
        );

        arr[2] = (new KitManager("Healer",
                new String[] {"§8✘ §eAusrüstung",
                        "§8✘ §73x Heilungstränke mit Heilung II",
                        "§8✘ §71 Regenerationstrank für 1:30 Minuten"},
                Material.GHAST_TEAR, 5000)
        );

        return arr;
    }
}
