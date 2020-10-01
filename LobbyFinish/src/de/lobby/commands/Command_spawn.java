package de.lobby.commands;

import de.lobby.main.Main;
import de.lobby.utils.SpawnFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_spawn implements CommandExecutor {
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        Player p = (Player)cs;
        if (cs instanceof Player &&
                cmd.getName().equalsIgnoreCase("setspawn"))
            if (p.hasPermission("server.owner")) {
                if (args.length != 0) {
                    p.sendMessage(Main.prefix + "Error: Benutze /setspawn");
                } else if (args.length == 0) {
                    SpawnFile.setSpawn("Spawn", p.getLocation());
                    p.sendMessage(Main.prefix + "Du hast den §r§eSpawn §r§aerfolgreich §r§7gesetzt.");
                }
            } else {
                p.sendMessage(Main.noPerms);
            }
        return true;
    }
}

