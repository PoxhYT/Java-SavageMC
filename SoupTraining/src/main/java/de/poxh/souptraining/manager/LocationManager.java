package de.poxh.souptraining.manager;

import de.poxh.souptraining.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocationManager {

    private static File file = new File("plugins/SoupTraining", "Locations.yml");

    private static FileConfiguration cfg = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

    public static void saveCfg() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setSpawn(String name, Location loc) {
        cfg.set(name + ".world", loc.getWorld().getName());
        cfg.set(name + ".x", Double.valueOf(loc.getX()));
        cfg.set(name + ".y", Double.valueOf(loc.getY()));
        cfg.set(name + ".z", Double.valueOf(loc.getZ()));
        cfg.set(name + ".yaw", Float.valueOf(loc.getYaw()));
        cfg.set(name + ".pitch", Float.valueOf(loc.getPitch()));
        saveCfg();
        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getConsoleSender().sendMessage(Main.prefix + "Spawn-Config wurde erfolgreich erstellt!");
            } catch (Exception exception) {}
        } else {
            Bukkit.getConsoleSender().sendMessage(Main.prefix + "Spawn-Config wurde erfolgreich geladen");
        }
    }

    public static Location getSpawn(String name) {
        World world = Bukkit.getWorld(cfg.getString(name + ".world"));
        double x = cfg.getDouble(name + ".x");
        double y = cfg.getDouble(name + ".y");
        double z = cfg.getDouble(name + ".z");
        Location loc = new Location(world, x, y, z);
        loc.setYaw(cfg.getInt(name + ".yaw"));
        loc.setPitch(cfg.getInt(name + ".pitch"));
        return loc;
    }
}
