package de.cba.commands;

import de.cba.main.Main;
import de.cba.manager.TeleportManager;
import de.cba.manager.TeleportType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_TPA implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(Main.prefix + "Du musst ein Spieler sein.");
            return false;
        }

        final Player player = ((Player) sender);

        if(args.length == 1){

            final Player target = Bukkit.getPlayer(args[0]);

            if(target != null){
                TeleportManager.sendRequest(player, target, TeleportType.NORMAL);
            }else{
                player.sendMessage(Main.prefix + "§cDer angegebene Spieler ist nicht online.");
            }
        }else{
            player.sendMessage(Main.prefix + "Usage: /tpa <Player>");
        }
        return false;
    }
}

