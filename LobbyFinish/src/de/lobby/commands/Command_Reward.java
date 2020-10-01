package de.lobby.commands;

import de.lobby.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_Reward implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.prefix + "§r§cDu musst ein Spieler sein!");
            return true;
        }
        if (args.length == 0) {
            if (!(Main.getInstance()).rewardManager.getAllowReward(p)) {
                p.sendMessage(Main.prefix + "§r§cDu hast deinen heutigen Reward schon abgeholt!");
                long current = System.currentTimeMillis();
                long release = (Main.getInstance()).rewardManager.getTime(p);
                long millis = release - current;
                p.sendMessage(((Main.getInstance()).prefix) + (Main.getInstance()).rewardManager.getRemainingTime(millis));
                return true;
            }
            p.sendMessage(Main.prefix + "Du hast deine §r§etägliche Belohnung §r§aabgeholt§7!");
            (Main.getInstance()).rewardManager.setReward(p);
            if ((Main.getInstance()).broadcast)
                Bukkit.broadcastMessage(Main.prefix + p.getDisplayName() + " §r§7hat seine §r§etägliche Belohnung §r§aabgeholt§7!");
            return true;
        }
        p.sendMessage(Main.prefix + "Benutze: /rewards");
        return true;
    }
}
