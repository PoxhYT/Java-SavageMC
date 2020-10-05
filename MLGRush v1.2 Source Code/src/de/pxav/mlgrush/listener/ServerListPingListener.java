package de.pxav.mlgrush.listener;

import de.pxav.mlgrush.enums.GameState;
import de.pxav.mlgrush.gamestates.GameStateHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    /**
     * This event handles the situation when the server
     * is pinged by another server or the server list.
     * It sets the motd for the current {@link GameState}.
     * You don't have to add game-states here. Just add
     * them in the {@link GameState} class and assign the
     * motd value including color-codes to it.
     * @param event The {@link ServerListPingEvent} object containing
     *              all information of the event.
     */
    @EventHandler
    public void onServerlistPing(final ServerListPingEvent event) {
        for (GameState gameState : GameState.values()) {
            if (gameState == GameStateHandler.getGameState()) {
                event.setMotd(GameStateHandler.getGameState().getMotd());
                break;
            }
        }
    }

}
