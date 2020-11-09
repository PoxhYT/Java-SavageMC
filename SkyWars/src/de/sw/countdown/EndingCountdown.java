package de.sw.countdown;

import de.sw.gameManager.GameStateManager;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EndingCountdown extends Countdown{

    private int seconds = 20;
    private boolean isRunning;
    private int idleID;
    private boolean isIdling;

    @Override
    public void start() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            public void run() {
                for (Player all : Main.alivePlayers) {
                    try {
                        all.setLevel(seconds);
                        String float_String = "0." + seconds;
                        float xp = Float.valueOf(float_String).floatValue();
                        xp *= 1.7F;
                        all.setExp(xp);
                    } catch (NumberFormatException e) {}
                }
                switch (seconds) {
                    case 20: case 15: case 10: case 5: case 4: case 3: case 2:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Main.prefix + "§cDer Server startet in " + seconds + " §cSekunden neu!");
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                        }
                        break;
                    case 1:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Main.prefix + "§cDer Server startet in 1 Sekunde neu!");
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                        }
                        break;
                    case 0:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.kickPlayer("§cDer Server startet neu!");
                            Bukkit.shutdown();
                        }
                        break;
                    default:
                        break;
                }
                seconds --;
            }
        }, 0, 20);

    }

    @Override
    public void stop() {
        if(isRunning) {
            Bukkit.getScheduler().cancelTask(taskID);
            isRunning = false;
            seconds = 20;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
