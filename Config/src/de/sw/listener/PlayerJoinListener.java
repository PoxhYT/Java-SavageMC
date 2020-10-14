package de.sw.listener;

import de.sw.api.Locations;
import de.sw.main.Main;
import de.sw.manager.SBManager;
import de.sw.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SBManager.setLobbyBoard(player);
        Main.scoreCD();
        Data.playing.add(player);
        Main.inventoryManager.LobbyInventory(player);
        Data.alive.add(player);


        event.setJoinMessage(Main.prefix + "§e" + player.getName() + " §7hat das Spiel betreten!");
        if(Bukkit.getOnlinePlayers().size() == 1) {
            Main.lobbyCountdown.start();
        }
    }
}
