package de.sw.listener;

import de.sw.api.LocationAPI;
import de.sw.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

    }

    public void teleportAllPlayers(Player player) {
        player.teleport(LocationAPI.getSpawn("TEST"));
    }
}
