package com.rosemite.services.listener;

import com.rosemite.models.service.common.IService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private IService service;
    public PlayerJoinListener(IService service) {
        this.service = service;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        service.getPlayerService().setPlayerServer(player.getUniqueId().toString(), player.getServer().getServerName());
    }
}
