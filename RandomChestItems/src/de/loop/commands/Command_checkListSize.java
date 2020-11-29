package de.loop.commands;

import de.loop.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_checkListSize implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            sender.sendMessage(Main.prefix + "§eDie Länge der Liste lautet: " + Main.itemStackList.size());
        }
        return false;
    }
}
