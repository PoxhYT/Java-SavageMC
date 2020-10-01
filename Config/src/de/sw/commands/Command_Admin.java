package de.sw.commands;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Command_Admin implements CommandExecutor {

    public static Main instance = Main.getInstance();

    public Command_Admin(final String command) {
        Bukkit.getPluginCommand(command).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("server.owner")) {
                if(args.length == 0) {
                    Main.getInstance().getInventoryManager().openSetupInventory(player);
                }
            }
        }

        return false;
    }
}
