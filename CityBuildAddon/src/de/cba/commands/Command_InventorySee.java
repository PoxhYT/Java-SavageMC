package de.cba.commands;

import de.cba.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_InventorySee implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("server.team")) {
                if(args.length == 1) {
                    Player open_Inv = Bukkit.getPlayerExact(args[0]);
                    if(open_Inv != null) {
                        player.openInventory(open_Inv.getInventory());
                        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP,1, 1);
                        player.sendMessage(Main.prefix + "Du befindest dich nun im §eInventar §7von: " + open_Inv.getDisplayName());
                    } else
                        player.sendMessage(Main.prefix + "§cDieser Spieler befindet sich aktuell nicht auf dem Server!");
                    player.playSound(player.getLocation(), Sound.VILLAGER_DEATH,1, 1);
                } else
                    player.sendMessage(Main.prefix + "Bitte benutze den Befehl /§cinvsee §7<§cNAME§7>");
            }
        }
        return false;
    }
}
