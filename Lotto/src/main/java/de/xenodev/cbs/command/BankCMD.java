package main.java.de.xenodev.cbs.command;

import main.java.de.xenodev.cbs.main.Main;
import main.java.de.xenodev.cbs.utils.MoneyAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankCMD implements CommandExecutor {
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
                p.sendMessage(Main.prefix + " §7Du hast die Bank von §e" + t.getName() + " §7auf §6" + amount + " §7gesetzt");
                MoneyAPI.setBank(p, amount);
            }else if(args[1].equalsIgnoreCase("remove")){
                p.sendMessage(Main.prefix + " §7Du hast der Bank von §e" + t.getName() + " §6" + amount + " §7Money entzogen");
                MoneyAPI.removeBank(p, amount);
            }else if(args[1].equalsIgnoreCase("add")){
                p.sendMessage(Main.prefix + " §7Du hast der Bank von §e" + t.getName() + " §6" + amount + " §7Money hinzugefügt");
                MoneyAPI.addBank(p, amount);
            }else{
                p.sendMessage(Main.prefix + " §7Bitte benutze §a/bank <player> <set, add, remove> [amount]");
            }
        }else if(args.length == 2){
            if(!p.hasPermission("cbs.admin")){
                return true;
            }
            Integer amount = Integer.valueOf(args[1]);
            if(args[0].equalsIgnoreCase("set")){
                p.sendMessage(Main.prefix + " §7Du hast deine Bank auf §6" + amount + " §7gesetzt");
                MoneyAPI.setBank(p, amount);
            }else if(args[0].equalsIgnoreCase("remove")){
                p.sendMessage(Main.prefix + " §7Du hast Dir §6" + amount + " §7Money auf der Bank entzogen");
                MoneyAPI.removeBank(p, amount);
            }else if(args[0].equalsIgnoreCase("add")){
                p.sendMessage(Main.prefix + " §7Du hast Dir §6" + amount + " §7Money auf der Bank hinzugefügt");
                MoneyAPI.addBank(p, amount);
            }else{
                p.sendMessage(Main.prefix + " §7Bitte benutze §a/bank <set, add, remove> [amount]");
            }
        }else if(args.length == 1){
            Player t = Bukkit.getPlayerExact(args[0]);
            if(!t.isOnline() || t == null){
                p.sendMessage("§cDer Spieler ist nicht online!");
                return true;
            }
            if(MoneyAPI.hasBank(t) == true){
                double bank = MoneyAPI.getBank(t);
                String last = String.format("%.2f", bank);
                p.sendMessage(Main.prefix + " §e" + t.getName() + " §7hat §6" + bank + " §7Money auf der Bank");
            }else{
                p.sendMessage(Main.prefix + " §7Der Spieler hat noch kein Bankkonto");
            }
        }else{
            if(MoneyAPI.hasBank(p) == true){
                double bank = MoneyAPI.getBank(p);
                String last = String.format("%.2f", bank);
                p.sendMessage(Main.prefix + " §7Du hast §6" + last + " §7Money auf der Bank");
            }else{
                p.sendMessage(Main.prefix + " §7Du hast noch kein Bankkonto");
                p.sendMessage(Main.prefix + " §7Kaufe dir ein Bankkonto mit /trade buy");
            }
        }

        return false;
    }
}