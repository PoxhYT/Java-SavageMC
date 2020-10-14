package de.sw.gameManager;

import de.sw.countdown.LobbyCountdown;
import de.sw.main.Main;
import org.bukkit.Bukkit;

public class Lobby_State extends Game_State {

    private LobbyCountdown countdown;

    public static final int MIN_PLAYERS = 1,
                            MAX_PLAYERS = 2;

    public Lobby_State(GameState_Manager gameStateManager) {
        countdown = new LobbyCountdown(gameStateManager);
    }

    public void start() {
        countdown.startIdle();
    }

    @Override
    public void stop() {
        Bukkit.broadcastMessage(Main.prefix + "INGAME!");
    }

    public LobbyCountdown getCountdown() {
        return countdown;
    }
}
