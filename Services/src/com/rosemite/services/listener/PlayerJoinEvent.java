package com.rosemite.services.listener;

import com.rosemite.services.helper.Log;
import com.rosemite.services.models.player.PlayerInfo;
import com.rosemite.services.services.player.PlayerService;
import com.rosemite.services.services.skywars.SkywarsService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    private final PlayerService playerService;
    private final SkywarsService skywarsService;

    public PlayerJoinEvent(PlayerService playerService, SkywarsService skywarsService) {
        this.playerService = playerService;
        this.skywarsService = skywarsService;
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerInfo playerInfo;

        if (!player.hasPlayedBefore()) {
            Log.d("Creating new Player");
            playerInfo = createNewPlayer(player);
        } else {
            playerInfo = playerService.getPlayerInfo(player.getUniqueId().toString());
            Log.d("Fetched PlayerInfo");
        }

        Log.d(playerInfo);
    }

    public PlayerInfo createNewPlayer(Player player) {
        // Add new Player to Database
        PlayerInfo playerInfo =  playerService.createNewPlayer(player);

        // Initialize Kits
        boolean wasSuccessful =  this.skywarsService.initializeKits(player.getUniqueId().toString());
        Log.d(wasSuccessful);

        return playerInfo;
    }
}
