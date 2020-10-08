package org.rosemite.services.helper;

import org.bukkit.Bukkit;

public class Log {
    public static void log(Object message) { Bukkit.getConsoleSender().sendMessage("§c[Log]: §f" + message); }
}
