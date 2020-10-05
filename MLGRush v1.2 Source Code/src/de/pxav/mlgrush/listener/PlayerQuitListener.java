package de.pxav.mlgrush.listener;

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.enums.GameState;
import de.pxav.mlgrush.enums.LocationType;
import de.pxav.mlgrush.enums.TeamType;
import de.pxav.mlgrush.gamestates.GameStateHandler;
import de.pxav.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author PXAV
 */

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final PlayerManager playerManager = new PlayerManager(event.getPlayer());
        final Player player = playerManager.getPlayer();

        event.setQuitMessage(MLGRush.getInstance().getPrefix() + "§a" + player.getName() + " §7hat das Spiel verlassen.");
        MLGRush.getInstance().getTeamHandler().playing.remove(player);

        if (GameStateHandler.getGameState() == GameState.LOBBY) {

            if(MLGRush.getInstance().getTeamHandler().hasTeamNone(player))
                MLGRush.getInstance().getTeamHandler().leaveTeam(player, TeamType.NONE);
            if(MLGRush.getInstance().getTeamHandler().hasTeam(player))
                MLGRush.getInstance().getTeamHandler().leaveTeam(player, MLGRush.getInstance().getTeamHandler().getPlayerTeam(player));

            if(Bukkit.getOnlinePlayers().size() == 2) {
                MLGRush.getInstance().getLobbyCountdown().stop();
                MLGRush.getInstance().getIdleCountdown().start();
                Bukkit.broadcastMessage(MLGRush.getInstance().getPrefix() + "§cDer Countdown wurde aufgrund mangelnder Spieler abgebrochen.");
                Bukkit.getOnlinePlayers().forEach(current -> current.playSound(current.getLocation(), Sound.NOTE_BASS, 3, 1));
            } else {
                MLGRush.getInstance().getIdleCountdown().stop();
                System.out.println("[MLGRUSH] Last player left. Stopped idle!");
            }

        } else if (GameStateHandler.getGameState() == GameState.INGAME) {

            if(MLGRush.getInstance().getTeamHandler().hasTeam(player)) {
                MLGRush.getInstance().getPlayerMoveScheduler().stopListening();
                Bukkit.getOnlinePlayers().forEach(current -> {
                    final PlayerManager currentManager = new PlayerManager(current);
                    if(MLGRush.getInstance().getConfigManager().isCacheLoader())
                        current.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY));
                    else current.teleport(MLGRush.getInstance().getLocationHandler().getLocationByFile(LocationType.LOBBY));
                    currentManager.sendTitle("§c§l" + player.getName(), "§7hat das Spiel verloren!");
                });
                MLGRush.getInstance().getEndingCountdown().start();
                MLGRush.getInstance().getTeamHandler().leaveTeam(player, MLGRush.getInstance().getTeamHandler().getPlayerTeam(player));
            }

        } else if (GameStateHandler.getGameState() == GameState.ENDING) {

        }

    }

}
