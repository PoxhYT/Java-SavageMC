package de.pxav.mlgrush.listener;

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.enums.GameState;
import de.pxav.mlgrush.enums.LocationType;
import de.pxav.mlgrush.enums.TeamType;
import de.pxav.mlgrush.gamestates.GameStateHandler;
import de.pxav.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {

        final PlayerManager playerManager = new PlayerManager(event.getPlayer());
        final Player player = playerManager.getPlayer();
        final MLGRush instance = MLGRush.getInstance();

        if (GameStateHandler.getGameState() == GameState.LOBBY) {
            MLGRush.getInstance().getTeamHandler().playing.add(player);
            event.setJoinMessage(MLGRush.getInstance().getPrefix() + "§a" + player.getName() + " §7hat das Spiel betreten.");
            if(MLGRush.getInstance().getConfigManager().isCacheLoader())
                player.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY));
            else
                player.teleport(MLGRush.getInstance().getLocationHandler().getLocationByFile(LocationType.LOBBY));
            instance.getScoreboardHandler().setScoreboard(player);
            playerManager.resetPlayer();
            instance.getInventoryManager().givePlayerLobbyItems(player);
            instance.getTeamHandler().joinTeam(player, TeamType.NONE);
            instance.getTabListHandler().setTabList(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.setFoodLevel(20);
            player.setHealth(20.0D);
            player.setHealthScale(20.0D);

            if (Bukkit.getOnlinePlayers().size() == 1) {
                instance.getIdleCountdown().start();
            } else if (Bukkit.getOnlinePlayers().size() == 2) {
                instance.getIdleCountdown().stop();
                instance.getLobbyCountdown().start(true, false);
            } else {
                player.kickPlayer(MLGRush.getInstance().getPrefix() + "§cDas Spiel ist voll!");
            }

            Bukkit.getScheduler().runTaskLater(MLGRush.getInstance(), () -> {
                if(MLGRush.getInstance().getConfigManager().isCacheLoader())
                    player.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY));
                else
                    player.teleport(MLGRush.getInstance().getLocationHandler().getLocationByFile(LocationType.LOBBY));
            }, 4);
        } else if (GameStateHandler.getGameState() == GameState.INGAME) {

            event.setJoinMessage(instance.getPrefix() + "§a" + player.getName() + " §7hat das Spiel als §aZuschauer §7betreten.");
            if(MLGRush.getInstance().getConfigManager().isCacheLoader())
                player.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPECTATOR));
            else
                player.teleport(MLGRush.getInstance().getLocationHandler().getLocationByFile(LocationType.SPECTATOR));
            playerManager.resetPlayer();
            instance.getTabListHandler().setTabList(player);
            player.setGameMode(GameMode.SPECTATOR);

            Bukkit.getScheduler().runTaskLater(MLGRush.getInstance(), () -> {
                if(MLGRush.getInstance().getConfigManager().isCacheLoader())
                    player.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPECTATOR));
                else
                    player.teleport(MLGRush.getInstance().getLocationHandler().getLocationByFile(LocationType.SPECTATOR));
            }, 4);

        }

    }

}
