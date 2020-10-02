package de.ticketapi.main;

import de.ticketapi.commands.Command_tickets;
import de.ticketapi.util.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    public static String prefix = "§8• §eCoinSystem §8⎟ §r§7";

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        getCommand("tickets").setExecutor(new Command_tickets());
        MySQL.setStandardMySQL();
        MySQL.readMySQL();
        MySQL.connect();
        MySQL.createTable();
    }

    public void onDisable() {
        MySQL.close();
    }

    private void loadconfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }
}


