package de.poxh.mlgrush.command;

import de.poxh.mlgrush.main.Main;
import de.poxh.mlgrush.manager.team.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Command_createTeam implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if(sender instanceof Player) {
            if(args.length == 2) {
                String teamName = args[0];
                int colorCode = Integer.valueOf(args[1]);
                ItemStack itemStack = player.getInventory().getItemInHand();

                if(itemStack != null) {
                    if(colorCode <= 9) {
                        TeamManager manager = new TeamManager();
                        manager.createTeam(teamName, itemStack, player, colorCode);
                    } else {
                        player.sendMessage(Main.prefix + "§c§lBitte gebe eine Zahl zwischen 1-9 an.");
                        return true;
                    }
                } else {
                    player.sendMessage(Main.prefix + "§cBitte halte ein Item in deine Hand, bevor du ein Team erstellen willst.");
                    return true;
                }


            }
        }


        return false;
    }
}
