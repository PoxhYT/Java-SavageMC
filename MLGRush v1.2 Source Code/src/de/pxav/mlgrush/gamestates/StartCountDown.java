package de.pxav.mlgrush.gamestates;

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
import org.bukkit.Sound;

public class StartCountDown {

    private int taskID;
    private int index;

    public void start() {
        index = 5;
        taskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(MLGRush.getInstance(), () -> {

            switch (index) {
                case 5 : case 3 : case 2 : case 1 :
                    MLGRush.getInstance().getTeamHandler().playing.forEach(current -> {
                        final PlayerManager playerManager = new PlayerManager(current);
                        playerManager.sendTitle("§a§l" + index, "");
                        current.playSound(current.getLocation(), Sound.ORB_PICKUP, 3, 1);
                    });
                    break;
                case 0:
                    MLGRush.getInstance().getTeamHandler().playing.forEach(current -> {
                        final PlayerManager playerManager = new PlayerManager(current);
                        playerManager.sendTitle("§a§lLOS!", "§7Das Spiel startet");
                        current.playSound(current.getLocation(), Sound.LEVEL_UP, 10F, 10F);
                    });
                    GameStateHandler.setAllowMove(true);
                    this.stop();
                    break;
            }

            index--;

        }, 0, 20);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
