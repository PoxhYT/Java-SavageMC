package de.sw.listener;

import de.sw.main.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;

public class PlayerInteractListener implements Listener {

    private File file = new File("plugins/SkyWars", "Config.yml");

    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    String gameSize = yamlConfiguration.getString("gameSize");

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        try {
            if(event.getItem().getType() == Material.BED) {
                Main.inventoryManager.openKitInventory(player);
            }
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
