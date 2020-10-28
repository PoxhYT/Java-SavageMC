package de.sw.listener;

import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import de.sw.manager.ItemBuilderAPI;
import de.sw.manager.KitManager;
import de.sw.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TeamListener implements Listener {

    private static File file = new File("plugins/SkyWars", "Map.yml");
    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public List<TeamManager> teams;

    public TeamListener() {
        // TODO: Create new SkywarsMapData
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).) {
                boolean isInTeam = teams.get(i).isInTeam(player);

                if (isInTeam) {
                    Log.d("Du bist schon in dem Team");
                } else {
                    // Todo: Add player to team & check if team is full
                }
                break;
            }
        }
    }

    public void onPlayerChangeTeam(Player player, String teamName) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getTeamName().equals(teamName)) {
                boolean isInTeam = teams.get(i).isInTeam(player);

                if (isInTeam) {
                    Log.d("Du bist schon in dem Team");
                } else {
                    // Todo: Add player to team & check if team is full
                }
                break;
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
