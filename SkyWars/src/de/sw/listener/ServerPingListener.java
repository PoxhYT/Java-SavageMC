package de.sw.listener;

import de.gamestateapi.main.GameStateAPIManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;


public class ServerPingListener implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        event.setMotd(GameStateAPIManager.getState().toString());
    }
}
