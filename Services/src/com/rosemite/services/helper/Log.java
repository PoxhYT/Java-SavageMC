package com.rosemite.services.helper;

import org.bukkit.Bukkit;

public class Log {
    public static void d(Object message) { Bukkit.getConsoleSender().sendMessage("§c[Log]: §f" + message); }
    public static void w(Object message) { Bukkit.getConsoleSender().sendMessage("§6[Log] Warning: §f" + message); }
}
