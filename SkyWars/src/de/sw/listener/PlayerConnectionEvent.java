package de.sw.listener;

import de.sw.countdown.LobbyCountdown;
import de.sw.gameManager.Lobby_State;
import de.sw.main.Main;
import de.sw.manager.UtilsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionEvent implements Listener {

    private Main instance;

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        if(!(Main.instance.getGameStateManager().getCurrentGame_State() instanceof Lobby_State)) return;
        Player player = event.getPlayer();
        Main.instance.players.add(player);
        event.setJoinMessage(Main.prefix + "§8» §e" + player.getDisplayName() + " §7hat das Spiel betreten! §7[§a"
                + Main.instance.players.size() + "§7/§c" + Lobby_State.MAX_PLAYERS + "§7]");

        Lobby_State lobbyState = (Lobby_State) Main.instance.getGameStateManager().getCurrentGame_State();
        LobbyCountdown countdown = lobbyState.getCountdown();
        if(Main.instance.players.size() >= Lobby_State.MIN_PLAYERS) {
            if(!countdown.isRunning()) {
                countdown.stopIdle();
                countdown.start();
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(!(Main.instance.getGameStateManager().getCurrentGame_State() instanceof Lobby_State)) return;
        Player player = event.getPlayer();
        Main.instance.players.remove(player);
        event.setQuitMessage(Main.prefix + "§8» §e" + player.getDisplayName() + " §7hat das Spiel verlassen! §7[§a"
                + Main.instance.players.size()+ "§7/§c" + Lobby_State.MAX_PLAYERS + "§7]");

        Lobby_State lobbyState = (Lobby_State) Main.instance.getGameStateManager().getCurrentGame_State();
        LobbyCountdown countdown = lobbyState.getCountdown();
        if(Main.instance.players.size() < Lobby_State.MIN_PLAYERS) {
            if(countdown.isRunning()) {
                countdown.stop();
                countdown.startIdle();
            }
        }
    }
}
