package de.sw.listener;

import de.sw.main.Main;
import de.sw.manager.InventoryManager;
import de.sw.manager.KitManager;
import de.sw.manager.SBManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerJoinListener implements Listener {

    public static Main instance = Main.instance;

    private File file = new File("plugins/SkyWars", "Config.yml");

    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    private SBManager sbManager = new SBManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Main.inventoryManager.setLobbyInventory(player);
        Integer test = yamlConfiguration.getInt("maxPlayers");
        sbManager.setLobbyBoard(player);

    }
}
