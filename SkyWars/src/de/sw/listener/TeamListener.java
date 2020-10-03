package de.sw.listener;

import de.sw.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class TeamListener implements Listener {

    private TeamManager[] teamManagers = new TeamManager[8];

    public void onInventoryClick(InventoryClickEvent event) {
        TeamManager[] teams = getTeams();

        try {
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem().getType() == Material.WOOL) {

            }

        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static TeamManager[] getTeams() {
        TeamManager[] teamManagers = new TeamManager[2];

        teamManagers[0] = (new TeamManager("Team1", "§e", (byte)1));
        teamManagers[1] = (new TeamManager("Team2", "§e", (byte)1));

        return teamManagers;
    }
}
