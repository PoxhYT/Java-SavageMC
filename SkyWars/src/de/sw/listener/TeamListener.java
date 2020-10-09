package de.sw.listener;

import de.sw.main.Main;
import de.sw.manager.ItemBuilderAPI;
import de.sw.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class TeamListener implements Listener {

    static File file = new File("plugins/SkyWars", "Config.yml");

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        TeamManager team1 = new TeamManager("§eTeam1", "§eTeam1", 2);

        try {
            if(event.getClickedInventory().getTitle().equals("§eTeamauswahl")) {
                if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§eTeam1")) {
                    team1.sizePlayers.add(player);
                }
            }

        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getPlayer().getItemInHand().getType() == Material.BED) {
            openTeamInventory(player);
        }
    }

    public void openTeamInventory(Player player) {

        TeamManager team1 = new TeamManager("§eTeam1", "§eTeam1", 2);
        TeamManager team2 = new TeamManager("§eTeam2", "§eTeam2", 2);

        Inventory inventory = Bukkit.createInventory(null, 9, "§eTeamauswahl");
        inventory.setItem(0, team1.getIcon());
        inventory.setItem(1, team2.getIcon());
        player.openInventory(inventory);

    }
}
