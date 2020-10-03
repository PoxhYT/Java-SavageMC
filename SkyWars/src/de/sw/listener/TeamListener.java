package de.sw.listener;

import de.sw.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class TeamListener implements Listener {

    private TeamManager team1,team2,team3,team4,team5,team6,team7,team8;

    public void onInventoryClick(InventoryClickEvent event) {
        team1 = new TeamManager("Team1", "§e", (byte)1);
        Player player = (Player) event.getWhoClicked();
    }

    public void openTeamInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§eTeamauswahl");
        inventory.setItem();
    }

}
