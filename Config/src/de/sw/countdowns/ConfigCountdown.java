package de.sw.countdowns;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.File;

public class ConfigCountdown extends Countdown{

    public int seconds = 3;

    private int taskID;

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
    @Override
    public void start() {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                switch (seconds) {
                    case 3: case 2: case 1:
                        for(Player all : Bukkit.getOnlinePlayers())
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                        break;
                    case 0:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_PLING, 1, 1);
                            all.sendMessage(Main.prefix + "§7Die §eConfig §7wurde erfolgreich §egespeichert§7.");
                            ConfigCountdown.this.stop();
                            break;
                        }
                }
                seconds--;
            }
        }, 0, 20);
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(this.taskID);
        seconds = 3;
    }
}
