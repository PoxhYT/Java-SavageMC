package de.sw.commands;

import de.sw.api.Locations;
import de.sw.main.Main;
import de.sw.utils.Map;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Command_Setup implements CommandExecutor {

    private Main instance = Main.instance;

    public Command_Setup(final String command) {
        Bukkit.getPluginCommand(command).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        File file = new File("plugins/Config", "config.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        if(sender instanceof Player) {
            int MaxTeams = configuration.getInt("MaxTeams");
            Player player = (Player) sender;
            if(player.hasPermission("server.owner")) {
                if(args.length == 0) {
                    player.sendMessage(Main.prefix + "§cBitte benutze §7/§esetup lobby");
                } else {
                    if (args[0].equalsIgnoreCase("lobby")) {
                        if(args.length == 1) {
                            Locations.setSpawn("Lobby", player.getLocation());
                            player.sendMessage(Main.prefix + "Die §eLobby §7wurde gesetzt.");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1,1);
                        } else
                            player.sendMessage(Main.prefix + "§cBitte benutze §7/§esetup lobby");
                    } else if(args[0].equalsIgnoreCase("create")) {
                        if(args.length == 3) {
                            Map map = new Map(instance, args[1]);
                            if(!map.exists()) {
                                map.create(args[2]);
                                player.sendMessage(Main.prefix + "Du hast die Map §e" + map.getName() + " §7erstellt!");
                                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1,1);
                            }else
                                player.sendMessage(Main.prefix + "§cDiese Map existiert bereits!");
                        }else
                            player.sendMessage(Main.prefix + "§cBitte nutze §7/§esetup §7<§ecreate§7> <§eNAME§7> <§eERBAUER§7>");
                    } else if(args[0].equalsIgnoreCase("set")) {
                        if (args.length == 3) {
                            Map map = new Map(instance, args[1]);
                            if(map.exists()) {
                                try {
                                    int spawnNumber = Integer.parseInt(args[2]);
                                    if(spawnNumber > 0 && spawnNumber <= MaxTeams) {
                                        map.setSpawnLocations(spawnNumber, player.getLocation());
                                        player.sendMessage(Main.prefix + "Du hast die §eSpawn§7-§eLocation §a" + spawnNumber + " §7für die Map §e" + map.getName() + " §7gesetzt.");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1,1);
                                    } else
                                        player.sendMessage(Main.prefix + "§cBitte gib eine Zahl zwischen 1 und " + MaxTeams + " an.");
                                }catch (NumberFormatException exception) {
                                    if(args[2].equalsIgnoreCase("spectator")) {
                                        Locations.setSpawn("Maps." + map.getName() + ".Spectator", player.getLocation());
                                        player.sendMessage(Main.prefix + "Du hast die §eSpectator§7-§eLocation §7für die Map §e" + map.getName() + " §7gesetzt.");
                                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1,1);
                                    }else
                                        player.sendMessage(Main.prefix + "§cBitte nutze §7/§esetup set §7<§eNAME§7> <§e1§7-§e" + MaxTeams + " SPECTATOR§7>");
                                }
                            }else
                                player.sendMessage(Main.prefix + "§cDiese Map existiert noch nicht!");
                        } else
                            player.sendMessage(Main.prefix + "§cBitte nutze §7/§esetup set §7<§eNAME§7> <§e1§7-§e" + MaxTeams + " SPECTATOR§7>");
                    }
                }
            } else
                player.sendMessage(Main.prefix + "§cDu hast dafür nicht genügend Rechte!");
        }
        return false;
    }
}
