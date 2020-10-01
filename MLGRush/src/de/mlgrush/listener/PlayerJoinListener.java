package de.mlgrush.listener;

import de.mlgrush.enums.GameState;
import de.mlgrush.enums.LocationType;
import de.mlgrush.enums.TeamType;
import de.mlgrush.gamestates.GameStateHandler;
import de.mlgrush.main.Main;
import de.mlgrush.utils.PlayerManager;
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
        final Main instance = Main.getInstance();

        if (GameStateHandler.getGameState() == GameState.LOBBY) {
            Main.getInstance().getTeamHandler().playing.add(player);
            event.setJoinMessage(Main.getInstance().getPrefix() + "§a" + player.getName() + " §7hat das Spiel betreten.");
            if(Main.getInstance().getConfigManager().isCacheLoader())
                player.teleport(Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY));
            else
                player.teleport(Main.getInstance().getLocationHandler().getLocationByFile(LocationType.LOBBY));
            instance.getScoreboardHandler().setScoreboard(player);
            playerManager.resetPlayer();
            instance.getInventoryManager().giveLobbyItems(player);
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
                player.kickPlayer(Main.getInstance().getPrefix() + "§cDas Spiel ist voll!");
            }

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                if(Main.getInstance().getConfigManager().isCacheLoader())
                    player.teleport(Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.LOBBY));
                else
                    player.teleport(Main.getInstance().getLocationHandler().getLocationByFile(LocationType.LOBBY));
            }, 4);
        } else if (GameStateHandler.getGameState() == GameState.INGAME) {

            event.setJoinMessage(instance.getPrefix() + "§a" + player.getName() + " §7hat das Spiel als §aZuschauer §7betreten.");
            if(Main.getInstance().getConfigManager().isCacheLoader())
                player.teleport(Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPECTATOR));
            else
                player.teleport(Main.getInstance().getLocationHandler().getLocationByFile(LocationType.SPECTATOR));
            playerManager.resetPlayer();
            instance.getTabListHandler().setTabList(player);
            player.setGameMode(GameMode.SPECTATOR);

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                if(Main.getInstance().getConfigManager().isCacheLoader())
                    player.teleport(Main.getInstance().getLocationHandler().getLocationFromCache(LocationType.SPECTATOR));
                else
                    player.teleport(Main.getInstance().getLocationHandler().getLocationByFile(LocationType.SPECTATOR));
            }, 4);

        }

    }

}




