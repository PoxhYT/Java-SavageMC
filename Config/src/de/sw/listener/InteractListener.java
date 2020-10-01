package de.sw.listener;

import de.sw.main.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;

public class InteractListener implements Listener {

    static File file = new File("plugins/Config", "config.yml");

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    @EventHandler
    public void onInt(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String size = yamlConfiguration.getString("Size");
        try {
            if(event.getItem().getType() == Material.BED) {
                Main.inventoryManager.openTeamInventory(player);
            }
        }catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }
}


