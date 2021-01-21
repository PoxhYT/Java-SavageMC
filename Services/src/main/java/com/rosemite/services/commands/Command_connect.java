package com.rosemite.services.commands;

import com.rosemite.services.main.Test;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class Command_connect implements CommandExecutor {
    private Test pluginMessage = new Test();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(command.getName().equalsIgnoreCase("connect")) {
                if(args.length == 1) {
                    pluginMessage.connect(player, args[0]);
                }

            }
        }
        return false;
    }
}
