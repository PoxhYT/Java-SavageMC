package de.cba.commands;

import de.cba.main.Main;
import de.cba.manager.TeleportManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_TPAccept implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Main.prefix + "Du musst ein Spieler sein.");
            return false;
        }

        final Player player = ((Player) sender);

        if(args.length == 0){

            TeleportManager.acceptLastRequest(player);

        }else if(args.length == 1){

            TeleportManager.acceptRequest(player, args[0]);

        }else{
            player.sendMessage(Main.prefix + "Usage: /tpaccept [Player]");
        }


        return false;
    }
}

