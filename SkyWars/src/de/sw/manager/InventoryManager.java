package de.sw.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.lang.reflect.Array;
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
            inventory.setItem(i, teams[i].getIcon());
        player.openInventory(inventory);
    }

    public static TeamManager[] getTeams() {
        TeamManager[] teamManagers = new TeamManager[2];

        teamManagers[0] = (new TeamManager("Team1", "§e", (byte) 1));
        teamManagers[1] = (new TeamManager("Team2", "§e", (byte) 1));

        return teamManagers;
    }

}
