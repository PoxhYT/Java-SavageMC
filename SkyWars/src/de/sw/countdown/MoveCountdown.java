package de.sw.countdown;

import de.sw.enums.Path;
import de.sw.gameManager.GameStateManager;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MoveCountdown extends Countdown{

    private static int seconds = 5;
    private boolean isRunning;
    private int idleID;
    private boolean isIdling;
    private Main instance;

    public void start() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                for (Player all : Main.alivePlayers) {
                    Main.moveMap.add(all);
                }
                switch (seconds) {
                    case 5: case 4: case 3: case 2:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                            all.sendTitle("§e" + seconds, "§4");
                        }
                        break;
                    case 1:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                            all.sendTitle("§e" + seconds, "§8");
                        }
                        break;
                    case 0:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.LEVEL_UP, 1, 1);
                            Main.moveMap.remove(all);
                            instance.protectionCountdown.start();
                        }
                        break;
                }
                seconds--;
            }
        }, 0, 20);
    }

    @Override
    public void stop() {

    }
}
