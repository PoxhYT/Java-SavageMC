package de.sw.manager;

import org.bukkit.command.CommandSender;

public class Util {
    private static Util instance;

    public static Util get() {
        if (instance == null)
            instance = new Util();
        return instance;
    }

    public boolean hp(String t, CommandSender sender, String s) {
        if (t.equalsIgnoreCase("sw"))
            return sender.hasPermission("sw." + s);
        if (t.equalsIgnoreCase("kit"))
            return sender.hasPermission("sw.kit." + s);
        if (t.equalsIgnoreCase("map"))
            return sender.hasPermission("sw.map." + s);
        if (t.equalsIgnoreCase("party"))
            return sender.hasPermission("sw.party." + s);
        return false;
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
