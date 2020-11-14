package de.sw.countdown;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ProtectionCountdown extends Countdown{

    private int timer = 30;

    @Override
    public void start() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                switch (timer) {
                    case 30: case 20: case 15: case 10: case 5: case 4: case 3: case 2:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Main.prefix + "Die §eSchutzzeit §7endet in §e" + timer + " Sekunden§7!");
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                        }
                        break;
                    case 1:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Main.prefix + "Die §eSchutzzeit §7endet in §e1 Sekunde§7!");
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                        }
                        break;
                    case 0:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Main.prefix + "Die §eSchutzzeit §7wurde beendet.");
                            all.playSound(all.getLocation(), Sound.LEVEL_UP, 1, 1);
                        }
                        break;
                    default:
                        break;
                }
                timer --;
            }
        }, 0, 20);
    }

    @Override
    public void stop() {

    }
}
