package de.sw.commands;


import de.sw.api.LocationAPI;
import de.sw.enums.Path;
import de.sw.main.Main;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Command_SkyWars implements CommandExecutor {
    private static File fileSkywars = new File("plugins/SkyWars", "config.yml");
    private static YamlConfiguration yamlConfigurationSkyWars = YamlConfiguration.loadConfiguration(fileSkywars);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(sender instanceof Player) {
            if (player.hasPermission("server.owner")) {
                if (args.length == 0) {
                    player.sendMessage("§8➥ §f/sw setloc <name>");
                } else {
                    if (args[1].equals("setloc")) {
                        if (args.length == 1) {
                            player.sendMessage("§8➥ §f/sw setloc <name>");
                            player.playSound(player.getLocation(), Sound.VILLAGER_DEATH, 1, 1);
                        } else
                            player.sendMessage("§8➥ §f/sw setloc <name>");
                    if (args.length == 2) {
                        try {
                            int spawnNumber1 = Integer.parseInt(args[2]);
                            if(spawnNumber1 > 0 && spawnNumber1 <= yamlConfigurationSkyWars.getInt("Settings.MaxPlayers")) {
                                LocationAPI.setSpawn(args[2], player.getLocation());
                                player.sendMessage(Main.prefix + "Du hast die §eLocation §7" + args[2] + " §7gesetzt!");
                            } else
                                player.sendMessage(Main.prefix + "Gebe eine Nummer zwischen 1 und " + yamlConfigurationSkyWars.getInt("Settings.MaxPlayers") + " ein!");
                        }catch (NumberFormatException e){}
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
