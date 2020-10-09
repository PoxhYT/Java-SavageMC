package de.sw.countdown;

import de.sw.main.Main;
import de.sw.manager.UtilsManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LobbyCountdown extends Countdown{
    public static boolean isIdling = true;

    public static boolean isRunning = false;

    public static int seconds = 60;

    private int idleID;

    private int taskID;

    public void start() {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player all : UtilsManager.onlinePlayers) {
                    all.setLevel(seconds);
                    String float_String = "0." + seconds;
                    float xp = Float.valueOf(float_String).floatValue();
                    xp *= 1.7F;
                    all.setExp(xp);
                }
                switch (seconds) {
                    case 60: case 50: case 40: case 30: case 20: case 10: case 5: case 4: case 3: case 2:
                        Bukkit.broadcastMessage(Main.prefix + "Das §eSpiel §7startet in " + seconds + " §eSekunden§7!");
                        for (Player all : UtilsManager.onlinePlayers)
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                        break;
                    case 1:
                        Bukkit.broadcastMessage(Main.prefix + "Das §eSpiel §7startet in 1 §eSekunde§7!");
                        for (Player all : UtilsManager.onlinePlayers)
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                        break;
                    case 0:
                        Bukkit.broadcastMessage(Main.prefix + "Alle §eSpieler §7werden zu ihre Inseln teleportiert!");
                        for (Player all : UtilsManager.onlinePlayers)
                            all.playSound(all.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                        stop();
                        break;
                }
                seconds--;
            }
        }, 0, 20);
    }

    public void stop() {
        seconds = 60;
        isIdling = false;
        isRunning = false;
        Bukkit.getScheduler().cancelTask(this.taskID);
        Bukkit.getScheduler().cancelTask(this.idleID);
    }

    public void idle() {
        Bukkit.getScheduler().cancelTask(this.taskID);
        seconds = 60;
        for (Player all : Bukkit.getOnlinePlayers())
            all.setLevel(60);
        this.idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)Main.getInstance(), new Runnable() {
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers())
                    all.playSound(all.getLocation(), Sound.ANVIL_BREAK, 1.0F, 1.0F);
                Bukkit.broadcastMessage(Main.prefix + "§cWarten auf weitere Spieler...");
            }
        },0L, 300L);
    }

    public void stopIdle() {
        if (isIdling) {
            isIdling = false;
            Bukkit.getScheduler().cancelTask(this.idleID);
        }
    }

    public void stopRunning() {
        if (isRunning) {
            isRunning = false;
            Bukkit.getScheduler().cancelTask(this.taskID);
        }
    }
}
