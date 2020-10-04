package de.sw.listener;

import de.sw.manager.ItemBuilderAPI;
import de.sw.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class TeamListener implements Listener {

    static File file = new File("plugins/SkyWars", "Config.yml");

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    private TeamManager[] teamManagers = new TeamManager[8];

    public void onInventoryClick(InventoryClickEvent event) {
        TeamManager[] teams = getTeams();

        try {
            for (int i = 0; i < teamManagers.length; i++) {
                if(event.getClickedInventory().getTitle().equals("§8• §eTeams")) {
                    if (event.getCurrentItem().getType() == Material.WOOL) {
                        if(event.getCurrentItem().getData().getData() == (byte)1) {
                            System.out.println("Found team");
                            System.out.println(teamManagers[i].getName());
                        }
                    }
                }
            }
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getPlayer().getItemInHand().getType() == Material.BED) {
            openTeamInventory(player);
        }
    }

    public void openTeamInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 9, "§8• §eTeams");
        player.openInventory(inventory);
    }

    public static TeamManager[] getTeams() {
        TeamManager[] teamManagers = new TeamManager[2];

        teamManagers[0] = (new TeamManager("Team1", "§e", (byte)1));
        teamManagers[1] = (new TeamManager("Team2", "§e", (byte)1));

        return teamManagers;
    }
}
