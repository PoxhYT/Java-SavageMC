package main.java.de.xenodev.cbs.command;

import main.java.de.xenodev.cbs.main.Main;
import main.java.de.xenodev.cbs.utils.TimeManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCMD implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;

        if(args.length == 1) {
            Player t = Bukkit.getPlayer(args[0]);
            p.sendMessage(Main.prefix + " §e§l" + t.getName() + "'s §7Onlinezeit: §6" + TimeManager.getHours(t) + "§7h §6" + TimeManager.getMinutes(t) + "§7min §6" + TimeManager.getSeconds(t) + "§7sec");
            return true;
        }else if(args.length == 0){
            p.sendMessage(Main.prefix + " §e§l" + "Deine" + " §7Onlinezeit: §6" + TimeManager.getHours(p) + "§7h §6" + TimeManager.getMinutes(p) + "§7min §6" + TimeManager.getSeconds(p) + "§7sec");
            return true;
        }

        return false;
    }
}
