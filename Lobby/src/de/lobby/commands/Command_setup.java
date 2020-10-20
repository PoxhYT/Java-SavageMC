package de.lobby.commands;

import de.lobby.api.LocationAPI;
import de.lobby.main.Main;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_setup implements CommandExecutor {

    private static Main instance;

    private static String[] arguments = new String[7];

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("server.owner")) {
                if(args.length == 0) {
                    player.sendMessage(Main.prefix + "§cBitte benutze§7: /§esetup setlocation §7<§eSPAWN, SKYWARS, BEDWARS, MLGRUSH, UHC, SPEEDUHC, COMMUNITY§7>");
                    player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                } else {
                    if(args[0].equalsIgnoreCase("setlocation")) {
                        if(args.length == 2) {
                            String[] args1 = getArgs();
                            for (int i = 0; i < args1.length; i++)
                            {
                                if(args[1].equalsIgnoreCase(args1[i])) {
                                    player.sendMessage(Main.prefix + "Du hast die §eLocation §7für §e" + args[1] + " §7gesetzt.");
                                    LocationAPI.setSpawn(args[1], player.getLocation());
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                                }
                            }
                        } else {
                            player.sendMessage(Main.prefix + "§cBitte benutze§7: /§fsetup setlocation SPAWN, SKYWARS, ");
                            player.sendMessage(Main.prefix + "§fBEDWARS, MLGRUSH, UHC, SPEEDUHC, COMMUNITY");
                            player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                        }
                    }
                }
            }
        }
        return false;
    }

    private String[] getArgs() {
        arguments[0] = "SPAWN";
        arguments[1] = "SKYWARS";
        arguments[2] = "BEDWARS";
        arguments[3] = "MLGRUSH";
        arguments[4] = "UHC";
        arguments[5] = "SPEEDUHC";
        arguments[6] = "SOUPTRAINING";
        return arguments;
    }
}
