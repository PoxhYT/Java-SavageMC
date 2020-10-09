package de.sw.manager;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class UtilsManager {

    public static File file = new File("plugins/SkyWars", "Config.yml");

    public static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public static boolean canMove = false;

    public static boolean canThrow = false;

    public static ArrayList<Player> onlinePlayers = new ArrayList<>();

    public static ArrayList<Player> spectator = new ArrayList<>();

    public static int minPlayers = yamlConfiguration.getInt("minPlayers");
}
