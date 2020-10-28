package de.sw.listener;

import com.rosemite.services.helper.Log;
import de.sw.main.Main;
import de.sw.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class TeamListener implements Listener {

    private static File file = new File("plugins/SkyWars", "Map.yml");
    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public TeamManager[] teams;

    public TeamListener() {
        SkyWarsMapData data = new SkyWarsMapData(yamlConfiguration, "1");

        teams = new TeamManager[data.maxTeamCount];
        for (int i = 0; i < teams.length; i++) {
            teams[i] = new TeamManager("Team" + i, "§cTeam"+i, Material.WOOL, data.maxPlayersInTeam);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        for (int i = 0; i < teams.length; i++) {
            if(event.getInventory().getTitle().equals("§eTeamauswahl")) {
                if (event.getCurrentItem().getItemMeta().getDisplayName() == teams[i].getTeamName()) {
                    if (teams[i].getTeamName().equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
                        boolean isInTeam = teams[i].isInTeam(player);
                        player.sendMessage(Main.prefix + "Du hast das §e" + teams[i].getTeamName() + " §7beigetreten!");

                        if (isInTeam) {
                            Log.d("Du bist schon in dem Team");
                        } else {
                            if (!teams[i].isFull()) {
                                teams[i].addPlayer(player);
                                Log.d("Du hast das Team: " + teams[i].getTeamName() + " idk...");
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    public void openTeamInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§eTeamauswahl");
        String size = yamlConfiguration.getString("maxTeams");
        Integer teams = yamlConfiguration.getInt("teams");
        if(size.equals("8x1")) {
            for (int i = 0; i < teams; i++) {
                inventory.setItem(i, new ItemBuilderAPI(Material.WOOL).setDisplayName("§eTeam" + i).build());
            }
        }
    }
}
