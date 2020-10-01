package de.ticketapi.commands;

import de.ticketapi.main.Main;
import de.ticketapi.util.TicketAPI;
import de.ticketapi.util.messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_tickets implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
        Player p = (Player)sender;
        if (args.length == 0)
            p.sendMessage(Main.prefix + messages.getPlayerTickets().replaceAll("%Tickets%", String.valueOf(TicketAPI.getTickets(String.valueOf(p.getUniqueId())))));
        if (p.hasPermission("server.owner")) {
            if (args.length == 1)
                p.sendMessage(Main.prefix + "§e/tickets §7<set> <add> <remove> <Spieler> <Anzahl>.");
            if (args.length == 2)
                p.sendMessage(Main.prefix + "§e/tickets §7<set> <add> <remove> <Spieler> <Anzahl>.");
            if (args.length == 3) {
                Player name = Bukkit.getServer().getPlayer(args[1]);
                if (args[0].equalsIgnoreCase("set")) {
                    if (name != null) {
                        TicketAPI.setTickets(String.valueOf(name.getUniqueId()), Integer.valueOf(args[2]));
                        p.sendMessage(Main.prefix + "Du hast die §r§eTickets §r§7von " + p.getDisplayName() + " §r§7auf §r§e" + args[2] + " §r§7gesetzt.");
                    } else {
                        p.sendMessage(Main.prefix + "§r§cDieser Spieler ist leider nicht online§7.");
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (name != null) {
                        TicketAPI.removeTickets(String.valueOf(name.getUniqueId()), Integer.valueOf(args[2]));
                        p.sendMessage(Main.prefix + "Du hast " + p.getDisplayName() + " §r§e" + args[2] + " §r§eTickets §r§centfernt§r§7.");
                        p.sendMessage(Main.prefix + "Aktuelle §r§eTickets §r§7von " + p.getDisplayName() + " §7: " + TicketAPI.getTickets(p.getUniqueId().toString()));
                    } else {
                        p.sendMessage(messages.getPrefix() + messages.Offline().replaceAll("%Player%", p.getName()));
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    if (name != null) {
                        TicketAPI.addTickets(String.valueOf(name.getUniqueId()), Integer.valueOf(args[2]));
                        p.sendMessage(messages.getPrefix() + messages.getCommandAdd().replaceAll("%Player%", p.getName()).replaceAll("%Tickets%", args[2]) + TicketAPI.getTickets(String.valueOf(name.getUniqueId())));
                    } else {
                        p.sendMessage(messages.getPrefix() + messages.Offline().replaceAll("%Player%", p.getName()));
                    }
                }
            }
        } else {
            p.sendMessage(messages.getKeineRechte());
            return false;
        }
        return false;
    }
}