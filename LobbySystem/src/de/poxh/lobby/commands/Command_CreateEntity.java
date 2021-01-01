package de.poxh.lobby.commands;

import de.poxh.lobby.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Command_CreateEntity implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("server.owner")) {
                if (args.length == 1) {
                    String name = args[0];
                    if(args[0].equalsIgnoreCase(name)) {
                        Main.entityManager.createVillager(player, name);
                    }
                }
            } else {
                player.sendMessage(Main.prefix + "§cKEINE RECHTE!");
            }
        } else {
            sender.sendMessage(Main.prefix + "§cDieser Command kann nur ein Spieler ausführen!");
        }
        return false;
    }
}
