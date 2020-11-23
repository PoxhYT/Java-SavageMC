package de.signsystem.main;


import net.md_5.bungee.api.ChatColor;

public class Var {

    public static String INGAME = ChatColor.translateAlternateColorCodes('&', Main.cfg.getString("Ingame"));

    public static String LOADING = ChatColor.translateAlternateColorCodes('&', Main.cfg.getString("Loading"));

    public static String FULL_LOBBY = ChatColor.translateAlternateColorCodes('&', Main.cfg.getString("FullLobby"));

    public static String LOBBY = ChatColor.translateAlternateColorCodes('&', Main.cfg.getString("Lobby"));

    public static String UNKNOWN = ChatColor.translateAlternateColorCodes('&', Main.cfg.getString("UnknownState"));

    public static String MAINTENANCE = ChatColor.translateAlternateColorCodes('&', Main.cfg.getString("Maintenance"));

    public static boolean HIDEINGAMESERVERS = Main.cfg.getBoolean("HideIngameServers");

    public static boolean PLAY_EFFECTS = Main.cfg.getBoolean("PlaySignEffects");

}
