package de.sw.commands;

import de.sw.enums.Path;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Command_setMotd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(sender instanceof Player) {
            if(player.hasPermission("server.owner")) {
                if(args.length == 0) {
                    player.sendMessage(Main.prefix + "Die aktuelle Motd lautet: " + Main.MapName1.get(Path.MapName.toString()));
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                } else
                    player.sendMessage(Main.prefix + "Bitte benutze den Befehl /setmotd");
            } else
                player.sendMessage(Main.noPerms);
        } else
            sender.sendMessage("SPIELER");
        return false;
    }
}
