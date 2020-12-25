package de.poxh.services.listener;

import de.poxh.services.main.MainService;
import de.poxh.services.models.player.PlayerInfo;
import de.poxh.services.services.player.PlayerService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinEvent implements Listener {

    private final PlayerService playerService;

    public PlayerJoinEvent(PlayerService playerService) {
        this.playerService = playerService;
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerInfo playerInfo = playerService.getPlayerInfo(player.getUniqueId().toString());

        if (playerInfo == null) {
            Bukkit.getConsoleSender().sendMessage(MainService.prefix + "§eCreating new Player: " + player.getDisplayName());
            createNewPlayer(player);
        } else {
            Bukkit.getConsoleSender().sendMessage(MainService.prefix + "§ePlayer already Exists: " + player.getDisplayName());
        }
    }

    private PlayerInfo createNewPlayer(Player player) {
        // Add new Player to Database
        PlayerInfo playerInfo =  playerService.createNewPlayer(player);
        return playerInfo;
    }
}
