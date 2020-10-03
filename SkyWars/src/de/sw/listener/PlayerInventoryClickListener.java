package de.sw.listener;

import de.sw.main.Main;
import de.sw.manager.InventoryManager;
import de.sw.manager.KitManager;
import de.sw.manager.TeamManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInventoryClickListener implements Listener {

    public TeamManager[] teamManagers;

    // public TeamManager teamManager1 = new TeamManager("§eTeam1", Material.WOOL, "§eTeam1");

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        try {
            for (int i = 0; i < teamManagers.length; i++) {
                if (event.getCurrentItem().getType() == teamManagers[i].material)
                {
                    System.out.println("Found team");
                    System.out.println(teamManagers[i].teamName);
                }
            }

        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
