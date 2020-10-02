package de.bungee.main;

import de.bungee.commands.Command_leave;
import de.bungee.commands.Command_ping;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;

    public static String prefix = "§eSavageMC §8❘ §7";


    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(prefix + "Das Plugin wurde erfolgreich gestratet!");
        registerCommands();

    }

    @Override
    public void onDisable() {
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(prefix + "§cDas Plugin wurde heruntergefahren!");
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public void registerCommands() {
        getCommand("leave").setExecutor((CommandExecutor)new Command_leave(this));
        getCommand("ping").setExecutor((CommandExecutor)new Command_ping());

    }
}
