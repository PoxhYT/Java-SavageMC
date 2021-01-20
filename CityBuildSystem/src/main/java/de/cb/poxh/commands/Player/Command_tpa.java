package de.cb.poxh.commands.Player;

import de.cb.poxh.manager.teleport.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command_tpa implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if(args[0].equalsIgnoreCase("request")) {
                    if(target != null) {
                        TeleportManager.sendRequest(player, target);
                    }
                }

                if(args[0].equalsIgnoreCase("accept")) {
                    TeleportManager.acceptRequest(player, args[1]);
                }
            }
        }

        return false;
    }
}
