package de.poxh.signsystem.command;

import de.poxh.signsystem.main.Main;
import de.poxh.signsystem.manager.ServerSign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_addSign implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("server.admin")) {
                if(args.length == 4) {
                    String tile = args[0];
                    String mapname = args[1];
                    Integer ip = Integer.parseInt(args[2]);
                    Integer port = Integer.parseInt(args[3]);
                }
            }
        }
        return false;
    }
}
