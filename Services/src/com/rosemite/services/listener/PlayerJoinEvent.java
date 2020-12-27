package com.rosemite.services.listener;

import com.rosemite.models.service.player.PlayerInfo;
import com.rosemite.services.main.MainService;
import com.rosemite.services.player.PlayerService;
import com.rosemite.services.skywars.SkywarsService;
import org.bukkit.Bukkit;
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
        PlayerInfo  playerInfo = playerService.getPlayerInfo(player.getUniqueId().toString());

        if (playerInfo == null) {
            Bukkit.getConsoleSender().sendMessage("§eCreating new Player: " + player.getDisplayName());
            createNewPlayer(player);
        } else {
            Bukkit.getConsoleSender().sendMessage("§ePlayer already Exists: " + player.getDisplayName());
        }
    }

    private PlayerInfo createNewPlayer(Player player) {
        // Add new Player to Database
        PlayerInfo playerInfo =  playerService.createNewPlayer(player);

        // Initialize Kits
        this.skywarsService.initializeKits(player.getUniqueId().toString());
        MainService.getService(null).getSoupTrainingService().initializePlayer(player);

        return playerInfo;
    }
}
