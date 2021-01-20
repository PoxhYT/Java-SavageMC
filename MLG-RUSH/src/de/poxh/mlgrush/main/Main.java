package de.poxh.mlgrush.main;

import de.poxh.mlgrush.command.Command_createTeam;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static String prefix = "§3§lMLG§7-§3§lRUSH §8❘ §7§l";


    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(Main.prefix + "§a§lSuccessfully loaded!");
        registerCommands();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerCommands() {
        getCommand("createTeam").setExecutor(new Command_createTeam());
    }
}
