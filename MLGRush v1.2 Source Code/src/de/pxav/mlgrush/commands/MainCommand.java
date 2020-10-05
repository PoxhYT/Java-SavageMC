package de.pxav.mlgrush.commands;

// The project Stream - MLGRush is developed and updated by PXAV.
// You are not allowed to claim this as your own, give it to 
// others or even sell it.
//
// Contact me on:
// YouTube: https://www.youtube.com/channel/UCtXSAGTwurKVeEbwEKMlFog
// Twitter: https://twitter.com/OrigPXAV
// 
// (c) 2018 PXAV     

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.enums.LocationType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor {

    public MainCommand(final String command) {
        Bukkit.getPluginCommand(command).setExecutor(this);
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] args) {

        if(commandSender instanceof Player) {
            final Player player = (Player) commandSender;
            if(player.hasPermission("mlgrush.admin")) {

                final String prefix = MLGRush.getInstance().getPrefix();

                if(args.length == 0) {
                    player.sendMessage(prefix + "§8§m--------------------");
                    player.sendMessage(prefix + "§7Auf diesem Server läuft §aMLGRush§7.");
                    player.sendMessage(prefix + "§7Version§8: §a" + MLGRush.getInstance().getDescription().getVersion());
                    player.sendMessage(prefix + "§8");
                    player.sendMessage(prefix + "§7Einrichten§8: §a/setup");
                    player.sendMessage(prefix + "§7Testen§8: §a/mlgrush loc <Name>");
                    player.sendMessage(prefix + "§7Testen§8: §a/mlgrush listLocs");
                    player.sendMessage(prefix + "§7Testen§8: §a/mlgrush conf");
                    player.sendMessage(prefix + "§8§m--------------------");
                } else if (args.length == 1 && args[0].equalsIgnoreCase("loc")) {
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Benutze §a/mlgrush loc <Name>");
                } else if (args.length == 2 && args[0].equalsIgnoreCase("loc")) {
                    final String locationName = args[1];
                    boolean found = false;
                    for (LocationType current : LocationType.values()) {
                        if(current.toString().equalsIgnoreCase(locationName.toUpperCase())) {
                            found = true;
                            try {
                                player.teleport(MLGRush.getInstance().getLocationHandler().getLocationFromCache(current));
                                player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du wurdest zu §a" + current.toString() + " §7teleportiet.");
                            } catch (NullPointerException e) {
                                player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDie Postition §7" + locationName.toUpperCase() + " §cwurde noch nicht gesetzt!");
                                return true;
                            }
                        }
                    }

                    if (!found)
                        player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDiese Position existiert nicht.");
                } else if (args.length == 1 && args[0].equalsIgnoreCase("listLocs")) {
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Geladene Positionen:");
                    int index = 1;
                    boolean foundOne = false;
                    for (LocationType current : LocationType.values()) {
                        if(MLGRush.getInstance().getLocationHandler().locations.get(current.toString()) != null) {
                            player.sendMessage(MLGRush.getInstance().getPrefix() + "§a" + index + ". §7" + current);
                            foundOne = true;
                        }
                        index++;
                    }
                    if(!foundOne)
                        player.sendMessage(MLGRush.getInstance().getPrefix() + "§cEs wurden keine Locations geladen.");
                } else if (args.length == 1 && args[0].equalsIgnoreCase("conf")) {
                    player.sendMessage(prefix + "§8§m--------------------");
                    player.sendMessage(prefix + "§aKonfiguration des Plugins§8:");
                    player.sendMessage(prefix + "§7Version§8: §a" + MLGRush.getInstance().getDescription().getVersion());
                    player.sendMessage(prefix + "§7CacheLoader§8: §a" + MLGRush.getInstance().getConfigManager().isCacheLoader());
                    player.sendMessage(prefix + "§7DamageCheck§8: §a" + MLGRush.getInstance().getConfigManager().isDamageCheck());
                    player.sendMessage(prefix + "§7ChatFormat§8: §a" + MLGRush.getInstance().getConfigManager().isUseChatFormat());
                    player.sendMessage(prefix + "§7WinPoints§8: §a" + MLGRush.getInstance().getConfigManager().getMaxPoints());
                    player.sendMessage(prefix + "§8§m--------------------");
                }
            } else
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDazu hast du keine Rechte.");
        } else
            commandSender.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu musst ein Spieler sein.");

        return false;
    }
}
