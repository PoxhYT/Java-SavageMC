package de.pxav.mlgrush.listener;

// The project Stream - MLGRush is developed and updated by PXAV.
// You are not allowed to claim this as your own, give it to 
// others or even sell it.
//
// Contact me on:
// YouTube: https://www.youtube.com/channel/UCtXSAGTwurKVeEbwEKMlFog
// Twitter: https://twitter.com/OrigPXAV
// 
// (c) 2018 PXAV     

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.enums.GameState;
import de.pxav.mlgrush.gamestates.GameStateHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(final PlayerLoginEvent event) {
        final Player player = event.getPlayer();

        if (GameStateHandler.getGameState() == GameState.ENDING)
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, MLGRush.getInstance().getPrefix() + "§cDas Spiel endet bereits...");
        else if(GameStateHandler.getGameState() == GameState.LOBBY && Bukkit.getOnlinePlayers().size() >= 2)
            event.disallow(PlayerLoginEvent.Result.KICK_FULL, MLGRush.getInstance().getPrefix() + "§cDas Spiel ist bereits voll.");
    }

}
