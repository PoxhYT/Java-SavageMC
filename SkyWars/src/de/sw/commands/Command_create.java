package de.sw.commands;

import de.sw.enums.Path;
import de.sw.main.Main;
import de.sw.manager.SkyWarsMapData;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bukkit.plugin.Plugin;

public class Command_create implements CommandExecutor {
    private Plugin plugin;

    public Command_create(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 0) {
            player.sendMessage(Main.prefix + "Bitte benutze den Befehl /create <name> <maxPlayersInTeam> <maxTeamCount> <Builder>");
        } else if(args.length == 4) {
            String name = args[0];
            int maxPlayersInTeam = Integer.parseInt(args[1]);
            int maxTeamCount = Integer.parseInt(args[2]);
            String builder = args[3];

            // Get Maps
            List<Map<String, Object>> maps = (List<Map<String, Object>>) Main.yamlConfigurationSkyWars.getList("maps");

            for (int i = 0; i < maps.size(); i++) {
                // Check if map name already exists. If so we exit & return. Else we save the map
                if (name.equalsIgnoreCase((String) maps.get(i).get(Path.MapName.toString()))) {
                    player.sendMessage(Main.prefix + "§cDie Map wurde bereits erstellt!");
                    return false;
                }
            }

            String gameSize = maxTeamCount + "x" + maxPlayersInTeam;

            // Save map
            SkyWarsMapData data = new SkyWarsMapData(maps.size() +1 , name, gameSize, new Location[0], maxTeamCount, maxPlayersInTeam, true);

            maps.add(SkyWarsMapData.toMap(data));

            Main.yamlConfigurationSkyWars.set("maps", maps);
            try {
                Main.yamlConfigurationSkyWars.save(Main.fileSkyWars);
            } catch (IOException e) {
                e.printStackTrace();
            }

            player.sendMessage(Main.prefix + "Du hast die Map §e" + name + " erfolgreich erstellt!");


            //Creates the new world.
            WorldCreator worldCreator = new WorldCreator(name);
            worldCreator.type(WorldType.FLAT);
            worldCreator.generatorSettings("0;0;1;");
            worldCreator.createWorld();

            Location blockLoc = new Location(Bukkit.getServer().getWorld(name), (double) 0, (double) 70, (double) 0);
            Location playerOnBlock = new Location(Bukkit.getServer().getWorld(name), (double) 0, (double) 71, (double) 0);
            blockLoc.getBlock().setType(Material.STONE);

            player.teleport(playerOnBlock);

        } else
            player.sendMessage(Main.prefix + "Bitte benutze den Befehl /create <name> <maxPlayersInTeam> <maxTeamCount> <Builder>");
        return false;
    }
}

