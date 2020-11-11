package de.cba.commands;

import de.cba.main.Main;
import javafx.print.PageLayout;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_DayandNight implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {

            Player player = (Player) sender;

            if(args.length == 0) {
                if(args[0].equalsIgnoreCase("tag")) {
                    Bukkit.getWorld("world").setTime(500);
                    player.sendMessage(Main.prefix + "Du hast die §eZeit §7zu §eTag §7geändert!");
                } else if(args[0].equalsIgnoreCase("tag")) {
                    Bukkit.getWorld("world").setTime(10000);
                    player.sendMessage(Main.prefix + "Du hast die §eZeit §7zu §eTag §7geändert!");
                } else
                    player.sendMessage(Main.prefix + "§7Bitte benutze den Befehl /<§etag§7>, <§enacht§7>!");
            } else
                player.sendMessage(Main.prefix + "§7Bitte benutze den Befehl /<§etag§7>, <§enacht§7>!");
        } else
            sender.sendMessage(Main.prefix + "§cDies kannst du nur als Spieler ausführen!");
        return false;
    }
}
