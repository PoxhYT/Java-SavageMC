package com.poxh.party.commands;

import com.poxh.party.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Command_party extends Command {
    public Command_party(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if(args.length == 0) {
                Main.getInstance().getManager().senHelp(player);
            } else if(args[0].equalsIgnoreCase("create")) {
                if(!Main.getInstance().getManager().isInParty(player)) {
                    Main.getInstance().getManager().createParty(player);
                } else {
                    player.sendMessage(new TextComponent(Main.Prefix + "§cDu hast bereits eine Party erstellt!"));
                }
            } else if(args[0].equalsIgnoreCase("leave")) {
                if(Main.getInstance().getManager().isInParty(player)) {
                    Main.getInstance().getManager().leave(player);
                    player.sendMessage(new TextComponent(Main.Prefix + "§cDu hast die Party verlassen!"));
                } else {
                    player.sendMessage(new TextComponent(Main.Prefix + "§cDu bist in keine Party!"));
                }
            }
        } else {
            sender.sendMessage(new TextComponent(Main.noConsole));
        }
    }
}
