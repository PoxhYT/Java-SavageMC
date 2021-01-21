package de.poxh.lobby.manager;

import de.poxh.lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScheduleManager {

    private Main instance;

    public ScheduleManager(Main instance) {
        this.instance = instance;
    }

    public void scoreCD() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.instance, () -> {
            for (Player all : Bukkit.getOnlinePlayers()) {
                SBManager.updateScoreboard(all);
            }
        }, 0, 5);
    }
}
