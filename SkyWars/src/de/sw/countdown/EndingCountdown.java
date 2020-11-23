package de.sw.countdown;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EndingCountdown extends Countdown{

    private int timer = 20;
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
                        all.setLevel(timer);
                        String float_String = "0." + timer;
                        float xp = Float.valueOf(float_String).floatValue();
                        xp *= 1.7F;
                        all.setExp(xp);
                    } catch (NumberFormatException e) {}
                }
                switch (timer) {
                    case 20: case 15: case 10: case 5: case 4: case 3: case 2:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.sendMessage(Main.prefix + "§cDer Server startet in " + timer + " §cSekunden neu!");
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
                            stop();
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
        if(isRunning) {
            Bukkit.getScheduler().cancelTask(taskID);
            isRunning = false;
            timer = 20;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
