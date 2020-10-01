package de.ticketapi.util;

import de.ticketapi.main.Main;
import org.bukkit.ChatColor;

public class messages {
    private static String Prefix = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Prefix"));

    private static String Command = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Command"));

    private static String Offline = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Offline"));

    private static String CommandSet = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("CommandSet"));

    private static String CommandRemove = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("CommandRemove"));

    private static String CommandAdd = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("CommandAdd"));

    private static String KeineRechte = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("KeineRechte"));

    private static String PlayerTickets = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("PlayerTickets"));

    public static String getPrefix() {
        return Prefix;
    }

    public static String getCommand() {
        return Command;
    }

    public static String Offline() {
        return Offline;
    }

    public static String getCommandSet() {
        return CommandSet;
    }

    public static String getCommandRemove() {
        return CommandRemove;
    }

    public static String getCommandAdd() {
        return CommandAdd;
    }

    public static String getKeineRechte() {
        return KeineRechte;
    }

    public static String getPlayerTickets() {
        return PlayerTickets;
    }
}