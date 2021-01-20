package de.cb.poxh.listener;

import de.cb.poxh.main.Main;
import de.cb.poxh.manager.player.SBManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Main.sbManager.setLobbyBoard(player);
    }
}
