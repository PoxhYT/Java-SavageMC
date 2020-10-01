package de.sw.countdowns;

import de.sw.api.Locations;
import de.sw.gamestate.GameState;
import de.sw.main.Main;
import de.sw.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class LobbyCountdown extends Countdown{
    public static boolean isIdling = true;

    public static boolean isRunning = false;

    public static int seconds = 60;

    private int idleID;

    private int taskID;

    static File file = new File("plugins/Config", "config.yml");

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public void start() {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.setLevel(seconds);
                    String float_String = "0." + seconds;
                    float xp = Float.valueOf(float_String).floatValue();
                    xp *= 1.7F;
                    all.setExp(xp);
                }
                switch (seconds) {
                    case 60: case 50: case 40: case 30: case 20: case 10:
                        for(Player all : Bukkit.getOnlinePlayers())
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                        Bukkit.broadcastMessage(Main.prefix + "Das §eSpiel §7startet in " + seconds + " §eSekunden§7!");
                        break;
                    case 5: case 4: case 3: case 2:
                        for(Player all : Bukkit.getOnlinePlayers()) {
                            all.sendTitle("§bSkyWars", "§e" + yamlConfiguration.getString("MapName"));
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                        }
                        Bukkit.broadcastMessage(Main.prefix + "Das §eSpiel §7startet in " + seconds + " §eSekunden§7!");
                        break;
                    case 1:
                        for(Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                            all.teleport(Locations.getSpawn("Lobby"));
                        }
                        Bukkit.broadcastMessage(Main.prefix + "Es wird die Map §e" + yamlConfiguration.getString("MapName") + " §7gespielt!");
                        Bukkit.broadcastMessage(Main.prefix + "Die Map wurde von §e" + yamlConfiguration.getString("Builder") + " §7gebaut!");
                    case 0:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.LEVEL_UP, 1, 1);
                            LobbyCountdown.this.stop();
                            break;
                        }
                }
                seconds--;
            }
        },0,20);
    }

    public void idle() {
        Bukkit.getScheduler().cancelTask(this.taskID);
        seconds = 60;
        for (Player all : Bukkit.getOnlinePlayers())
            all.setLevel(60);
        this.idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)Main.getInstance(), new Runnable() {
            int missing = Data.MIN_PLAYERS - Data.playing.size();

            public void run() {
                for (Player all : Bukkit.getOnlinePlayers())
                    all.playSound(all.getLocation(), Sound.ANVIL_BREAK, 1.0F, 1.0F);
                    Bukkit.broadcastMessage(Main.prefix + "§cEs fehlen " + Main.getInstance().getConfig().getInt("MinPlayers") + ", damit das Spiel starten kann!");
            }
        },0L, 300L);
    }


    @Override
    public void stop() {
        seconds = 60;
        isIdling = false;
        isRunning = false;
        Bukkit.getScheduler().cancelTask(this.taskID);
        Bukkit.getScheduler().cancelTask(this.idleID);
        Main.state = GameState.RESTART;
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

