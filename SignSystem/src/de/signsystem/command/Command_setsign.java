package de.signsystem.command;

import de.signsystem.main.Main;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.Command;

public class Command_setsign implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("signsystem.setup")) {
                if (args.length == 4) {
                    int port = 0;
                    try {
                        port = Integer.parseInt(args[2]);
                    } catch (NumberFormatException error) {
                        p.sendMessage(String.valueOf(Main.prefix) + "Nutze: /addsign <Servername> <IP> <Port> <Gruppe>");
                        return true;
                    }
                    String name = args[0];
                    String ip = args[1];
                    String group = args[3];
                    if (ip.equalsIgnoreCase("localhost"))
                        ip = "127.0.0.1";
                    Main.setup = String.valueOf(p.getName()) + ";" + name + ";" + ip + ";" + port + ";" + group;
                    p.sendMessage(String.valueOf(Main.prefix) + "Schlage nun das Schild!");
                } else {
                    p.sendMessage(String.valueOf(Main.prefix) + "Nutze: /addsign <Servername> <IP> <Port> <Gruppe>");
                }
            } else {
                p.sendMessage(Main.noPerms);
            }
        }
        return false;
    }
}

