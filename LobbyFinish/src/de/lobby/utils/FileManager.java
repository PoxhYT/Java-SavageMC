package de.lobby.utils;

import java.io.File;
import java.io.IOException;

import de.lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
    public static File getConfigFile() {
        return new File(Main.getInstance().getDataFolder(), "config.yml");
    }
    public static void createFile() {
        File file = new File("plugins/LobbySystem", "config.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            Bukkit.getConsoleSender().sendMessage(Main.prefix + "Eine Config wurde im Plugin-Ordner erstellt!");
            yamlConfiguration.set("MySQL.Passwort", "DeinMySQLPW");
            yamlConfiguration.set("Main.SilentLobby", Boolean.valueOf(false));
            try {
                yamlConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(Main.prefix + "Die Einstellungen aus der Config wurden erfolgreich geladen!");
            return;
        }
    }

    public static void update(String path, String newstring) {
        File file = new File("plugins/LobbySystem", "config.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        yamlConfiguration.set(path, newstring);
    }

    public static boolean getBoolean(String path) {
        File file = new File("plugins/LobbySystem", "config.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        return yamlConfiguration.getBoolean(path);
    }

    public static String getStringRoh(String path) {
        File file = new File("plugins/LobbySystem", "config.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        return yamlConfiguration.getString(path);
    }

    public static String getString(String path) {
        File file = new File("plugins/LobbySystem", "config.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        return yamlConfiguration.getString(path);
    }

    public static void loadConfig() {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(getConfigFile());
    }

    public String getRemainingTime(long millis) {
        long seconds = millis / 1000L;
        long minutes = 0L;
        while (seconds > 60L) {
            seconds -= 60L;
            minutes++;
        }
        long hours = 0L;
        while (minutes > 60L) {
            minutes -= 60L;
            hours++;
        }
        return "§7" + hours + " Stunde(n) " + minutes + " Minute(n) " + seconds + " Sekunde(n)";
    }
}

