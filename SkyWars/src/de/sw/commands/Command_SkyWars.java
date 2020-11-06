package de.sw.commands;


import de.sw.api.LocationAPI;
import de.sw.main.Main;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_SkyWars implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(sender instanceof Player) {
            if (player.hasPermission("server.owner")) {
                if (args.length == 0) {
                    player.sendMessage("§8➥ §f/sw setloc <name>");
                } else {
                    if(args.length == 1) {
                        if (args[1].equals("setloc")) {
                            player.sendMessage("§8➥ §f/sw setloc <name>");
                            player.playSound(player.getLocation(), Sound.VILLAGER_DEATH, 1, 1);
                        }
                    }

                    if (args.length == 2) {
                        if (args[1].equals("lobby")) {
                            LocationAPI.setSpawn("Lobby", player.getLocation());
                            player.sendMessage(Main.prefix + "§7Du hast die §eLobby §7erfolgreich gesetzt!");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        }
                    }
                }
            }
        }
        return false;
    }
}
