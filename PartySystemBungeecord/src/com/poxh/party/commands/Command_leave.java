package com.poxh.party.commands;

import com.poxh.party.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Command_leave extends Command {
    public Command_leave(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if(sender instanceof ProxiedPlayer) {
            ServerInfo target = ProxyServer.getInstance().getServerInfo("Lobby");
            if(!player.getServer().getInfo().getName().equalsIgnoreCase("Lobby")) {
                player.connect(target);
            } else {
                player.sendMessage(new TextComponent(Main.Prefix + "§cDu bist bereits in der Lobby!"));
            }

        } else {
            sender.sendMessage(new TextComponent(Main.noConsole));
        }
    }
}
