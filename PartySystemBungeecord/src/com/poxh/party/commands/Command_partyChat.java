package com.poxh.party.commands;

import com.poxh.party.Main;
import com.poxh.party.manager.PartyManager;
import com.poxh.party.manager.PlayerParty;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Command_partyChat extends Command {
    public Command_partyChat() {
        super("p");
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TextComponent(Main.Prefix+ "§e/p <Nachricht>"));
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer)sender;
        if (PartyManager.getParty(p) == null) {
            p.sendMessage(new TextComponent(Main.Prefix + "§cDu bist in keiner Party."));
            return;
        }
        PlayerParty party = PartyManager.getParty(p);
        String msg = "";
        String[] arrayOfString = args;
        int j = arrayOfString.length;
        int i = 0;
        while (i < j) {
            String s = arrayOfString[i];
            msg = String.valueOf(String.valueOf(msg)) + "§7 " + s;
            i++;
        }
        for (ProxiedPlayer members : party.getMembers())
            members.sendMessage(new TextComponent(Main.Prefix + p.getDisplayName() + " §8➥ §7" + msg + "§7" + "§7" + "§7"));
            party.getLeader().sendMessage(new TextComponent(Main.Prefix + p.getDisplayName() + " §8➥ §7" + msg + "§7" + "§7" + "§7"));
    }
}
