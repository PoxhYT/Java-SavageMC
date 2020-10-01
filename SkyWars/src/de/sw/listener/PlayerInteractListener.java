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

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            String gameSize = yamlConfiguration.getString("gameSize");
            if (event.getItem().getType() == Material.BED) {
                Main.inventoryManager.openKitInventory(player);
            }

            if(event.getItem().getType() == Material.MUSHROOM_SOUP) {
                player.setHealth(3);
            }
        }catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }
}
