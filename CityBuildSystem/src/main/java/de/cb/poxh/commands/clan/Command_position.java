package de.cb.poxh.commands.clan;

import de.cb.poxh.main.Main;
import de.cb.poxh.manager.clan.ClanManager;
import de.cb.poxh.manager.clan.PlayerClans;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Command_position implements CommandExecutor {

    private static Map<String,Map<String, Location>> locations = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 2) {

            String name = args[1];
            PlayerClans clan;

            if(args[0].equalsIgnoreCase("save")) {
                if(ClanManager.getClan(player) != null) {

                    clan = ClanManager.getClan(player);
                    if(locations.get(clan.getName()) == null) {
                        locations.put(clan.getName(), new HashMap<>());
                    }

                    Map<String, Location> clanLocs = locations.get(clan.getName());
                    player.sendMessage(String.valueOf(locations.get(clan.getName())));

                    if(!clanLocs.containsKey(name)) {
                        Map<String, Location> clanLocation = locations.get(clan.getName());
                        clanLocation.put(name, player.getLocation());
                        locations.put(clan.getName(), clanLocation);
                        Location location = clanLocs.get(name);
                        player.sendMessage(Main.instance.prefix + "Du hast die Location §e" + name + " §7gespeichert");
                        player.sendMessage(Main.instance.prefix + "Die §eKoordinaten §7von " + name + " §7befinden sich unter " + "X: " + location.getX() + " Y: " +
                                location.getY() + " Z: " + location.getZ());
                    } else {
                        player.sendMessage(Main.instance.prefix + "§7Die §eLocation " + name + " §7befindet sich bereits in der Liste!");
                    }

                } else {
                    player.sendMessage(Main.instance.prefix + "Erstelle zu erst einen Clan bevor du Locations abspeicherst");
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("get")) {
                if(ClanManager.getClan(player) != null) {
                    clan = ClanManager.getClan(player);
                    Map<String, Location> clanLocs = locations.get(clan.getName());
                    Location location = clanLocs.get(name);
                    if(clanLocs.containsKey(name)) {
                       player.sendMessage(Main.instance.prefix + "§e" + name + " §7befindet sich unter den §eKoordinaten §7" + location.getX() + " Y: " +
                               location.getY() + " Z: " + location.getZ());
                    } else {
                        player.sendMessage(Main.instance.prefix + "§cDiese Location existiert nicht");
                    }

                } else {
                    player.sendMessage(Main.instance.prefix + "Erstelle zu erst einen Clan bevor du Locations abspeicherst");
                    return true;
                }
            }
        }

        return false;
    }
}
