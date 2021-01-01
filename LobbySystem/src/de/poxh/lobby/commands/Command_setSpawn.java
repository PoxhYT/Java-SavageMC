package de.poxh.lobby.commands;

import de.poxh.lobby.api.LocationAPI;
import de.poxh.lobby.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_setSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("server.owner")) {
                if(args.length == 1) {
                    String name = args[0];
                    player.sendMessage(Main.prefix + "Du hast die §e" + name + "-Location §7gesetzt!");
                    LocationAPI.setSpawn(name, player.getLocation());
                } else {
                    player.sendMessage(Main.prefix + "Benutze den Befehl §7/§esetspawn name");
                }
            } else {
                player.sendMessage(Main.prefix + "§cKEINE RECHTE!");
            }
        } else {
            sender.sendMessage(Main.prefix + "§cNur Spieler können diesen Befehl ausführen!");
        }
        return false;
    }
}
