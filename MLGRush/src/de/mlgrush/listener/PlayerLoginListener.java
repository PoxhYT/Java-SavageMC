package de.mlgrush.listener;

import de.mlgrush.enums.GameState;
import de.mlgrush.gamestates.GameStateHandler;
import de.mlgrush.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (GameStateHandler.getGameState() == GameState.ENDING) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Main.prefix + "§cDas Spiel endet bereits...");
        } else if (GameStateHandler.getGameState() == GameState.LOBBY && Bukkit.getOnlinePlayers().size() >= 2) {
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, Main.prefix + "§cDas Spiel ist bereits voll.");
        }
    }
}

