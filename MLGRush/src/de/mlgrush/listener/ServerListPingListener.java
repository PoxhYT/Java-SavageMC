package de.mlgrush.listener;

import de.mlgrush.enums.GameState;
import de.mlgrush.gamestates.GameStateHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        for (GameState gameState : GameState.values()) {
            if (gameState == GameStateHandler.getGameState()) {
                event.setMotd(GameStateHandler.getGameState().getMotd());
                break;
            }
        }
    }
}
