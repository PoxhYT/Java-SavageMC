package de.mlgrush.listener;

import de.mlgrush.enums.GameState;
import de.mlgrush.enums.LocationType;
import de.mlgrush.enums.TeamType;
import de.mlgrush.gamestates.GameStateHandler;
import de.mlgrush.main.Main;
import de.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final PlayerManager playerManager = new PlayerManager(event.getPlayer());
        final Player player = playerManager.getPlayer();

        event.setQuitMessage(Main.getInstance().getPrefix() + "§a" + player.getName() + " §7hat das Spiel verlassen.");
        Main.getInstance().getTeamHandler().playing.remove(player);

        if (GameStateHandler.getGameState() == GameState.LOBBY) {

            if(Main.getInstance().getTeamHandler().hasTeamNone(player))
                Main.getInstance().getTeamHandler().leaveTeam(player, TeamType.NONE);
            if(Main.getInstance().getTeamHandler().hasTeam(player))
                Main.getInstance().getTeamHandler().leaveTeam(player, Main.getInstance().getTeamHandler().getPlayerTeam(player));

            if(Bukkit.getOnlinePlayers().size() == 2) {
                Main.getInstance().getLobbyCountdown().stop();
                Main.getInstance().getIdleCountdown().start();
                Bukkit.broadcastMessage(Main.getInstance().getPrefix() + "§cDer Countdown wurde aufgrund mangelnder Spieler abgebrochen.");
                Bukkit.getOnlinePlayers().forEach(current -> current.playSound(current.getLocation(), Sound.NOTE_BASS, 3, 1));
            } else {
                Main.getInstance().getIdleCountdown().stop();
                System.out.println("[MLGRUSH] Last player left. Stopped idle!");
            }

        } else if (GameStateHandler.getGameState() == GameState.INGAME) {

            if(Main.getInstance().getTeamHandler().hasTeam(player)) {
                Main.getInstance().getPlayerMoveScheduler().stopListening();
                Bukkit.getOnlinePlayers().forEach(current -> {
                    final PlayerManager currentManager = new PlayerManager(current);
                    if(Main.getInstance().getConfigManager().isCacheLoader())
                        current.teleport(Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY));
                    else current.teleport(Main.getInstance().getLocationHandler().getLocationByFile(LocationType.LOBBY));
                    currentManager.sendTitle("§c§l" + player.getName(), "§7hat das Spiel verloren!");
                });
                Main.getInstance().getEndingCountdown().start();
                Main.getInstance().getTeamHandler().leaveTeam(player, Main.getInstance().getTeamHandler().getPlayerTeam(player));
            }

        } else if (GameStateHandler.getGameState() == GameState.ENDING) {

        }

    }

}

