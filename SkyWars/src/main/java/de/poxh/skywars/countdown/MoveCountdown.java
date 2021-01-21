package de.sw.countdown;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveCountdown extends Countdown{

    private int timer = 5;
    private static boolean isRunning;
    private int idleID;
    private boolean isIdling;
    private Main instance;

    public void start() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                switch (timer) {
                    case 5: case 4: case 3: case 2:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                            all.sendTitle("§e" + timer, "§4");
                        }
                        break;
                    case 1:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                            all.sendTitle("§e" + timer, "§8");
                        }
                        break;
                    case 0:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.LEVEL_UP, 1, 1);
                            Main.moveMap.remove(all);
                            Main.protectionCountdown.start();
                        }
                        break;
                }
                timer--;
            }
        }, 0, 20);
    }

    @Override
    public void stop() {

    }
}
