package main.java.de.xenodev.cbs.command;

import main.java.de.xenodev.cbs.main.Main;
import main.java.de.xenodev.cbs.utils.MoneyAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCMD implements CommandExecutor {
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
                p.sendMessage(Main.prefix + " §7Du hast das Money von §e" + t.getName() + " §7auf §6" + amount + " §7gesetzt");
                MoneyAPI.setMoney(t, amount);
            }else if(args[1].equalsIgnoreCase("remove")){
                p.sendMessage(Main.prefix + " §7Du hast §e" + t.getName() + " §6" + amount + " §7Money entzogen");
                MoneyAPI.removeMoney(t, amount);
            }else if(args[1].equalsIgnoreCase("add")){
                p.sendMessage(Main.prefix + " §7Du hast §e" + t.getName() + " §6" + amount + " §7Money hinzugefügt");
                MoneyAPI.addMoney(t, amount);
            }else{
                p.sendMessage(Main.prefix + " §7Bitte benutze §a/money <player> <set, add, remove> [amount]");
            }
        }else if(args.length == 2){
            if(!p.hasPermission("cbs.admin")){
                return true;
            }
            Integer amount = Integer.valueOf(args[1]);
            if(args[0].equalsIgnoreCase("set")){
                p.sendMessage(Main.prefix + " §7Du hast dein Money auf §6" + amount + " §7gesetzt");
                MoneyAPI.setMoney(p, amount);
            }else if(args[0].equalsIgnoreCase("remove")){
                p.sendMessage(Main.prefix + " §7Du hast Dir §6" + amount + " §7Money entzogen");
                MoneyAPI.removeMoney(p, amount);
            }else if(args[0].equalsIgnoreCase("add")){
                p.sendMessage(Main.prefix + " §7Du hast Dir §6" + amount + " §7Money hinzugefügt");
                MoneyAPI.addMoney(p, amount);
            }else{
                p.sendMessage(Main.prefix + " §7Bitte benutze §a/money <set, add, remove> [amount]");
            }
        }else if(args.length == 1){
            Player t = Bukkit.getPlayerExact(args[0]);
            if(!t.isOnline() || t == null){
                p.sendMessage("§cDer Spieler ist nicht online!");
                return true;
            }
            double money = MoneyAPI.getMoney(t);
            String last = String.format("%.2f", money);
            p.sendMessage(Main.prefix + " §e" + t.getName() + " §7hat §6" + last + " §7Money");
        }else{
            double money = MoneyAPI.getMoney(p);
            String last = String.format("%.2f", money);
            p.sendMessage(Main.prefix + " §7Du hast §6" + last + " §7Money");
        }

        return false;
    }
}
