package de.lobby.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TimeManager {

    public static File file = new File("plugins//MineTronix//Saves//time.yml");
    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public static int getSeconds(OfflinePlayer target) {
        if(cfg.get(target.getUniqueId().toString() + ".Seconds") != null) {
            int amount = (int) cfg.get(target.getUniqueId().toString() + ".Seconds");
            return amount;
        }
        return 0;
    }

    public static int getMinutes(OfflinePlayer p) {
        if(cfg.get(p.getUniqueId().toString() + ".Minutes") != null) {
            int amount = (int) cfg.get(p.getUniqueId().toString() + ".Minutes");
            return amount;
        }
        return 0;
    }

    public static int getHours(OfflinePlayer p) {
        if(cfg.get(p.getUniqueId().toString() + ".Hours") != null) {
            int amount = (int) cfg.get(p.getUniqueId().toString() + ".Hours");
            return amount;
        }
        return 0;
    }

    public static void addSeconds(OfflinePlayer p, Integer seconds) {
        int amount = getSeconds(p) + seconds;
        cfg.set(p.getUniqueId().toString() + ".Seconds", amount);
        save();
    }

    public static void addMinutes(OfflinePlayer p, Integer minutes) {
        int amount = getMinutes(p) + minutes;
        cfg.set(p.getUniqueId().toString() + ".Minutes", amount);
        save();
    }

    public static void addHours(OfflinePlayer p, Integer hours) {
        int amount = getHours(p) + hours;
        cfg.set(p.getUniqueId().toString() + ".Hours", amount);
        save();
    }

    public static void setSeconds(OfflinePlayer p, Integer seconds) {
        cfg.set(p.getUniqueId().toString() + ".Seconds", seconds);
        save();
    }

    public static void setMinutes(OfflinePlayer p, Integer minutes) {
        cfg.set(p.getUniqueId().toString() + ".Minutes", minutes);
        save();
    }

    public static void setHours(OfflinePlayer p, Integer hours) {
        cfg.set(p.getUniqueId().toString() + ".Hours", hours);
        save();
    }

    public static void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}