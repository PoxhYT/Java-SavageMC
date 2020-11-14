package main.java.de.xenodev.cbs.command;

import main.java.de.xenodev.cbs.main.Main;
import main.java.de.xenodev.cbs.utils.ChestLottery;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TicketCMD implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;

        // /coins player set amount

        if(args.length == 3){
            if(!p.hasPermission("cbs.admin")){
                return true;
            }
            Player t = Bukkit.getPlayerExact(args[0]);
            if(!t.isOnline() || t == null){
                p.sendMessage("§cDer Spieler ist nicht online!");
                return true;
            }

            Integer amount = Integer.valueOf(args[2]);
            if(args[1].equalsIgnoreCase("set")){
                p.sendMessage(Main.prefix + " §7Du hast die Tickets von §e" + t.getName() + " §7auf §6" + amount + " §7gesetzt");
                ChestLottery.setLottery(t, amount);
            }else if(args[1].equalsIgnoreCase("remove")){
                p.sendMessage(Main.prefix + " §7Du hast §e" + t.getName() + " §6" + amount + " §7Tickets entzogen");
                ChestLottery.removeLottery(t, amount);
            }else if(args[1].equalsIgnoreCase("add")){
                p.sendMessage(Main.prefix + " §7Du hast §e" + t.getName() + " §6" + amount + " §7Tickets hinzugefügt");
                ChestLottery.addLottery(t, amount);
            }else{
                p.sendMessage(Main.prefix + " §7Bitte benutze §a/ticket <player> <set, add, remove> [amount]");
            }
        }else if(args.length == 2){
            if(!p.hasPermission("cbs.admin")){
                return true;
            }
            Integer amount = Integer.valueOf(args[1]);
            if(args[0].equalsIgnoreCase("set")){
                p.sendMessage(Main.prefix + " §7Du hast deine Tickets auf §6" + amount + " §7gesetzt");
                ChestLottery.setLottery(p, amount);
            }else if(args[0].equalsIgnoreCase("remove")){
                p.sendMessage(Main.prefix + " §7Du hast Dir §6" + amount + " §7Tickets entzogen");
                ChestLottery.removeLottery(p, amount);
            }else if(args[0].equalsIgnoreCase("add")){
                p.sendMessage(Main.prefix + " §7Du hast Dir §6" + amount + " §7Tickets hinzugefügt");
                ChestLottery.removeLottery(p, amount);
            }else{
                p.sendMessage(Main.prefix + " §7Bitte benutze §a/ticket <set, add, remove> [amount]");
            }
        }else{
            p.sendMessage(Main.prefix + " §7Bitte benutze §a/ticket <player> <set, add, remove> [amount]");
        }

        return false;
    }
}
