package de.sw.countdown;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class RestartCountdown extends Countdown{

    private int timer = 10;
    private static boolean isRunning;
    private int idleID;
    private boolean isIdling;
    private Main instance;

    public void start() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                switch (timer) {
                    case 10: case 5: case 4: case 3: case 2:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                            Bukkit.broadcastMessage(Main.prefix + "§cDer Server startet in " + timer + " Sekunden neu!");
                        }
                        break;
                    case 1:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                            Bukkit.broadcastMessage(Main.prefix + "§cDer Server startet in 1 Sekunde neu!");
                        }
                        break;
                    case 0:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.LEVEL_UP, 1, 1);
                            all.kickPlayer(all.getName());
                            Bukkit.shutdown();
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
