package de.pxav.mlgrush.handler;

// The project Stream - MLGRush is developed and updated by PXAV.
// You are not allowed to claim this as your own, give it to 
// others or even sell it.
//
// Contact me on:
// YouTube: https://www.youtube.com/channel/UCtXSAGTwurKVeEbwEKMlFog
// Twitter: https://twitter.com/OrigPXAV
// 
// (c) 2018 PXAV     

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PointsHandler {

    private int taskID;

    public String getGlobalPoints() {
        int index = 0;
        StringBuilder stringBuilder = new StringBuilder("§a");
        for (Player current : MLGRush.getInstance().getTeamHandler().playing) {
            final PlayerManager playerManager = new PlayerManager(current);
            stringBuilder.append(playerManager.getRoundPoints());
            if(index == 0)
                stringBuilder.append(" §8| §a");
            index++;
        }
        return stringBuilder.toString();
    }

    public final int getMaxPoints() {
        return MLGRush.getInstance().getConfigManager().getMaxPoints();
    }

    public void sendPointsActionBar() {
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(MLGRush.getInstance(), () -> {

            MLGRush.getInstance().getTeamHandler().playing.forEach(current -> {

                final PlayerManager playerManager = new PlayerManager(current);
                playerManager.sendActionBar(MLGRush.getInstance().getPrefix() + "§7Du musst noch §a" + (getMaxPoints() - playerManager.getRoundPoints()) + "x §7das Bett deines Gegners abbauen.");

            });

        }, 0, 5);
    }

    public void stopPointsActionBar() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
