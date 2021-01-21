package de.cb.poxh.commands;

import de.cb.poxh.main.Main;
import de.cb.poxh.manager.InventoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_CB implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(args.length == 0) {
            InventoryManager manager = new InventoryManager();
            manager.openMenu(player);
        }
        return false;
    }
}
