package de.cb.poxh.api;

import com.rosemite.helper.Log;
import de.cb.poxh.commands.countdowns.Countdown_ProtectionTime;
import de.cb.poxh.main.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class WorldAPI {

    private static File file = new File("plugins/CityBuildSystem", "Worlds.yml");
    private static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public static void saveCfg() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createWorld(String name, Player player) {
        File fileWorld = new File(Bukkit.getServer().getWorldContainer(), name);
        ArrayList<String> list = (ArrayList<String>) cfg.getStringList("Worlds");
        if(!list.contains(name) && !fileWorld.exists()) {
            player.sendMessage(Main.instance.prefix + "Die §4§lWelt " + name + " §7wird erstellt.");
            WorldCreator wc = new WorldCreator(name);
            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.NORMAL);
            wc.createWorld();
            list.add(name);
            cfg.set("Worlds", list);
            saveCfg();
            player.sendMessage(Main.instance.prefix + "Die §4§lWelt§7: §4§l" + name + " §7wurde §a§lerfolgreich §7erstellt.");

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception exception) {}
            }
        } else {
            player.sendMessage(Main.instance.prefix + "Die §4§lWelt§7: §4§l" + name + " §7wurde bereits erstellt.");
        }
    }

    public static void randomTeleport(String name, Player player) {
        File fileWorld = new File(Bukkit.getServer().getWorldContainer(), name);
        if(fileWorld.exists()) {

            Log.d(player.getLocation().getWorld() + "OKAY");

            Random random = new Random();
            int x = random.nextInt(30000000) - 15000000;
            int y = 100;
            int z = random.nextInt(30000000) - 15000000;


            Location randomLocation = new Location(Bukkit.getWorld(name), x, y, z);
            if (randomLocation.getBlock().getBlockData().getMaterial() != Material.AIR) {
                randomTeleport(name, player);
                return;
            }

            if (randomLocation.getBlock().getBlockData().getMaterial() == Material.LAVA) {
                randomTeleport(name, player);
                return;
            }

            if (randomLocation.getBlock().getBiome().name().toLowerCase().contains("ocean")) {
                randomTeleport(name, player);
                return;
            }

            if(!Main.instance.countdown_delay.delayForCountdown.containsKey(player)) {
                if (!Main.instance.countdown_protectionTime.countdowns.containsKey(player) || Bukkit.getScheduler().isCurrentlyRunning(Main.instance.countdown_protectionTime.getTaskId())) {
                    Main.instance.countdown_protectionTime.registerCountdown(player, 20);
                    Countdown_ProtectionTime countdown_protectionTime = new Countdown_ProtectionTime();
                    countdown_protectionTime.registerCountdown(player, 20);
                    countdown_protectionTime.runTaskTimer(Main.getInstance(), 0, 20);
                    player.teleport(randomLocation);

                } else {
                    player.sendMessage(Main.instance.prefix + "§4§lDu wurdest bereits teleportiert bitte warte...");
                }
            } else {
                player.sendMessage(Main.instance.prefix + "§4§lDein TeleportDelay ist noch aktiv bitte warte §4§lbevor du dich erneut teleportieren möchtest");
            }
        } else {
            player.sendMessage(Main.instance.prefix + "§c§lDie Welt " + name + " konnte nicht gefunden werden!");
        }

    }

    public static void removeWorld(String name, Player player) {
        File fileWorld = new File(Bukkit.getServer().getWorldContainer(), name);
        ArrayList<String> list = (ArrayList<String>) cfg.getStringList("Worlds");

        if(list.contains(name) && fileWorld.exists()) {
            cfg.getStringList("Worlds").remove(name);
            saveCfg();
            Bukkit.unloadWorld(name, false);
            player.sendMessage("4");
            fileWorld.delete();
            player.sendMessage("5");
            player.sendMessage(Main.instance.prefix + "Du hast die §4§lWelt §7: §4§l" + name + " §7erfolgreich entfernt");
        }
    }
}
