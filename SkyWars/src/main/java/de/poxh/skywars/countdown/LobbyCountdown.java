package de.sw.countdown;

import com.rosemite.services.skywars.KitItem;
import de.gamestateapi.main.GameStateAPIManager;
import de.sw.enums.Path;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class LobbyCountdown extends Countdown{

    public static int seconds = 60;
    private boolean isRunning;
    private int idleID;
    private boolean isIdling;

    private File file = new File("plugins/SkyWars", "Config.yml");
    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

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
                    } catch (NumberFormatException e) { }
                }
                switch (seconds) {
                    case 60: case 50: case 40: case 30: case 20: case 10:
                        Bukkit.broadcastMessage(Main.prefix + "Das Spiel startet in " + seconds + " §eSekunden§7!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                        }
                        break;
                    case 5:
                        Bukkit.broadcastMessage(Main.prefix + "Aktuelle Map: §e" + Main.MapName1.get(Path.MapName.toString()));
                        Bukkit.broadcastMessage(Main.prefix + "Spielvariante: §8(§e" + Main.MapName1.get(Path.GameSize.toString()) + "§8)");
                        Bukkit.broadcastMessage(Main.prefix + "Das Spiel startet in " + seconds + " §eSekunden§7!");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                            String map = (String) Main.MapName1.get(Path.MapName.toString());
                            all.sendTitle("§eSkyWars","§f" + map);
                        }
                        break;
                    case 4: case 3: case 2:
                        Bukkit.broadcastMessage(Main.prefix + "Das Spiel startet in " + seconds + " §eSekunden§7!");
                        for (Player all : Bukkit.getOnlinePlayers())
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                        break;
                    case 1:
                        Bukkit.broadcastMessage(Main.prefix + "Das Spiel startet in 1 §eSekunde§7!");
                        for (Player all : Bukkit.getOnlinePlayers())
                            all.playSound(all.getLocation(), Sound.NOTE_BASS, 1, 1);
                        Main.build.addAll(Bukkit.getOnlinePlayers());
                        break;
                    case 0:
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.playSound(all.getLocation(), Sound.LEVEL_UP, 1, 1);
                            Main.getInstance().sbManager.setIngameBoard(all);
                            all.getInventory().clear();
                            Main.moveMap.add(all);
                            Main.moveCountdown.start();

                            Main.chestManager.onGameStart();

                            // Teleport Player
                            for (int i = 0; i < Main.instance.teams.length; i++) {
                                Main.instance.teams[i].teleportPlayers();
                            }


                            // Give Player their kits
                            Main.kitMap.forEach((player, kit) -> {
                                List<KitItem> kitItems = kit.getKitItem();
                                for (int i = 0; i < kitItems.size(); i++) {
                                    player.getInventory().addItem(kitItems.get(i).getItem());
                                }
                            });
                        }
                        GameStateAPIManager.setState(GameStateAPIManager.INGAME);
                        break;
                }
                seconds--;
            }
        }, 0, 20);
    }

    public void stop() {
        if(isRunning) {
            Bukkit.getScheduler().cancelTask(taskID);
            isRunning = false;
            seconds = 60;
        }
    }

    public void startIdle() {
        isIdling = true;
        idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(Main.prefix + "§cWarten auf weitere Spieler...");
                for (Player all : Bukkit.getOnlinePlayers())
                    all.playSound(all.getLocation(), Sound.ANVIL_BREAK, 1, 1);
            }
        }, 0, 400);
    }

    public void stopIdle() {
        if(isIdling) {
            Bukkit.getScheduler().cancelTask(idleID);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
