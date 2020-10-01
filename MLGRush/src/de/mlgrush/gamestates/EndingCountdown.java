package de.mlgrush.gamestates;

import de.mlgrush.enums.GameState;
import de.mlgrush.main.Main;
import de.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EndingCountdown {

    private int taskID;
    private int countDown;

    public void start() {
        countDown = 20;

        Main.getInstance().getPointsHandler().stopPointsActionBar();
        Main.getInstance().getStartCountdown().stop();
        Main.getInstance().getPlayerMoveScheduler().stopListening();
        GameStateHandler.setGameState(GameState.ENDING);

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {

            for (Player current : Bukkit.getOnlinePlayers()) {
                final PlayerManager playerManager = new PlayerManager(current);
                Main.getInstance().getInventoryManager().giveLobbyItems(current);
                current.getInventory().setItem(0, new ItemStack(Material.AIR));
                playerManager.sendActionBar(Main.getInstance().getPrefix() + " §7Der Server startet in §a" + countDown + " §7Sekunden neu.");
            }

            switch (countDown) {
                case 20 : case 15 : case 10 : case 5 : case 3 : case 2 :
                    Bukkit.broadcastMessage(Main.getInstance().getPrefix() + "§7Der Server startet in §a" + countDown + " §7Sekunden neu.");
                    break;
                case 1 :
                    Bukkit.broadcastMessage(Main.getInstance().getPrefix() + "§7Der Server startet in §aeiner §7Sekunde neu.");
                    break;
                case 0:
                    Bukkit.getOnlinePlayers().forEach(current -> current.kickPlayer(Main.getInstance().getPrefix() + "§7Der Server startet neu."));
                    this.stop();
                    Bukkit.shutdown();
            }

            countDown--;
        }, 0, 20);
    }

    private void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}

