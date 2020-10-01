package de.mlgrush.handler;

import de.mlgrush.main.Main;
import de.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PointsHandler {

    private int taskID;

    public String getGlobalPoints() {
        int index = 0;
        StringBuilder stringBuilder = new StringBuilder("§a");
        for (Player current : Main.getInstance().getTeamHandler().playing) {
            final PlayerManager playerManager = new PlayerManager(current);
            stringBuilder.append(playerManager.getRoundPoints());
            if(index == 0)
                stringBuilder.append(" §8| §a");
            index++;
        }
        return stringBuilder.toString();
    }

    public final int getMaxPoints() {
        return Main.getInstance().getConfigManager().getMaxPoints();
    }

    public void sendPointsActionBar() {
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), () -> {

            Main.getInstance().getTeamHandler().playing.forEach(current -> {
                final PlayerManager playerManager = new PlayerManager(current);
                playerManager.sendActionBar(Main.prefix + "§7Du musst noch §a" + (getMaxPoints() - playerManager.getRoundPoints()) + "x §7das Bett deines Gegners abbauen.");

            });

        }, 0, 5);
    }

    public void stopPointsActionBar() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
