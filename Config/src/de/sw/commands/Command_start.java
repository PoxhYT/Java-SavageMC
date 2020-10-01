package de.sw.commands;

import de.sw.countdowns.LobbyCountdown;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_start implements CommandExecutor {
    public Command_start(final String command) {
        Bukkit.getPluginCommand(command).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(sender instanceof Player) {
            if(args.length == 0) {
                LobbyCountdown.seconds = 15;
                for (Player all : Bukkit.getOnlinePlayers())
                    all.sendMessage(Main.prefix + "Das §eSpiel §7wurde beschleunigt!");
            }
        }

        return false;
    }
}
