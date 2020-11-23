package de.sw.commands;

import de.sw.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_setup implements CommandExecutor {

    private boolean spawnsSet = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if(sender instanceof Player) {
            if (args.length == 0) {
                player.sendMessage(Main.prefix + "Willkommen im §eSetup§7!");
            }
        }
        return false;
    }
}
