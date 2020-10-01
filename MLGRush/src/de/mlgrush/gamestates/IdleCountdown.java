package de.mlgrush.gamestates;


import de.mlgrush.enums.GameState;
import de.mlgrush.main.Main;
import de.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;

public class IdleCountdown {

    private static int taskID;

    public void start() {
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), () -> {
            if(GameStateHandler.getGameState() == GameState.LOBBY && Bukkit.getOnlinePlayers().size() == 1) {
                Bukkit.getOnlinePlayers().forEach(current -> {
                    final PlayerManager playerManager = new PlayerManager(current);
                    playerManager.sendActionBar(Main.prefix + "§cWarte auf §7einen §cweiteren Spieler.");
                });
            }
        }, 0L, 500L);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
