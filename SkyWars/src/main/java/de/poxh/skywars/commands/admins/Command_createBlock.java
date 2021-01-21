package de.sw.commands.admins;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_createBlock implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(player.hasPermission("server.owner")) {
            if(args.length == 0) {
                Location blockLoc = new Location(player.getWorld(), (double) 0, (double) 70, (double) 0);
                blockLoc.getBlock().setType(Material.STONE);
                player.sendMessage(Main.prefix + "§3Es wurde erfolgreich ein Steinblock bei den koordinaten x: 0, y:70, z:0 gesetzt!");
            } else {
                player.sendMessage(Main.prefix + "§cBitte benutze den Befehl /createBlock!");
            }
        }
        return false;
    }
}
