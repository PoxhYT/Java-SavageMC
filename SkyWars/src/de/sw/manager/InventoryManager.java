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
    }

    public void setTeamInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 9, "§8• §eTeams");
        TeamManager[] teams = getTeams();
        for (int i = 0; i < teams.length; i++)
        player.openInventory(inventory);
    }

    public static TeamManager[] getTeams() {
        TeamManager[] teamManagers = new TeamManager[2];

        return teamManagers;
    }

}
