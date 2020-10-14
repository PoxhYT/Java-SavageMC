package de.sw.listener;

import de.sw.api.LocationAPI;
import de.sw.main.Main;
import de.sw.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.ArrayList;

public class TeamListener implements Listener {

    static File file = new File("plugins/SkyWars", "Config.yml");
    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public static TeamManager team1 = new TeamManager("§eTeam1", "§eTeam1", 2);
    public static TeamManager team2 = new TeamManager("§eTeam2", "§eTeam2", 2);
    public static TeamManager team3 = new TeamManager("§eTeam3", "§eTeam3", 2);
    public static TeamManager team4 = new TeamManager("§eTeam4", "§eTeam4", 2);

    private void openTeamInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§eTeamauswahl");
        String size = yamlConfiguration.getString("gameSize");
        if(size.equals("4x2")) {
            TeamManager team1 = new TeamManager("§eTeam1", "§eTeam1", 2);
            TeamManager team2 = new TeamManager("§eTeam2", "§eTeam2", 2);
            TeamManager team3 = new TeamManager("§eTeam3", "§eTeam3", 2);
            TeamManager team4 = new TeamManager("§eTeam4", "§eTeam4", 2);
            inventory.setItem(0, team1.getIcon());
            inventory.setItem(1, team2.getIcon());
            inventory.setItem(2, team3.getIcon());
            inventory.setItem(3, team4.getIcon());

        }
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getItem().getType() == Material.BED) {
            openTeamInventory(player);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        try {
            if(event.getClickedInventory().getTitle().equals("§eTeamauswahl")) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§eTeam1")) {
                    addToTeam(player, team1);
                }
            }
        }catch (NullPointerException e){}
    }

    public void addToTeam(Player player, TeamManager teamManager) {
        if(!teamManager.isInTeam(player) && teamManager.getMaxPlayers() > teamManager.getPlayers().size()) {
            teamManager.getPlayers().add(player.getName());
            player.sendMessage(Main.prefix + "Du hast das " + teamManager.getTeamName() + " §7beigetreten!");
        } else {
            player.sendMessage(Main.prefix+ "§cDu kannst dieses Team nicht betreten!");
        }
    }
}
