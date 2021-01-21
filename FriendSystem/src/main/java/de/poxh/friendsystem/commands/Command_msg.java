package de.poxh.friends.commands;

import de.poxh.friends.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Command_msg extends Command {
    public Command_msg(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String message = "";
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if(args.length == 2) {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);

            if(target != null) {

                for (int i = 0; i < args.length; i++) {
                    message = message + args[0] + " ";
                }
                Main.sendMessage(Main.prefix + player.getDisplayName() + " §7» " + target.getDisplayName() + " §7» " + message, player);
                Main.sendMessage(Main.prefix + player.getDisplayName() + " §7» " + target.getDisplayName() + " §7» " + message, target);
                message = "";
            } else {
                Main.sendMessage(Main.prefix + "§c" + target.getName() + " ist nicht online!", player);
            }
        }
    }
}
