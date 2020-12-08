package de.sw.commands;

import de.sw.api.LocationAPI;
import de.sw.enums.Path;
import de.sw.main.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command_location implements CommandExecutor {
    private List<Location> locations = new ArrayList<>();
    public static String name;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<Map<String, Object>> maps = (List<Map<String, Object>>) Main.yamlConfigurationSkyWars.getList("maps");

        Player player = (Player) sender;
        if (sender instanceof Player) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("set")) {
                    int locationsSize = locations.size() + 1;
                    player.sendMessage(Main.prefix + "§eDie Location für das Team# " + locationsSize + " wurde gesetzt!");
                    locations.add(player.getLocation());
                } else if(args[0].equalsIgnoreCase("clear")) {
                    locations.clear();
                    player.sendMessage(Main.prefix + "§cDie Location List wurde erfolgreich gecleart!");
                } else if (args[0].equalsIgnoreCase("setlobby")) {
                    LocationAPI.setSpawn("Lobby", player.getLocation());
                    player.sendMessage(Main.prefix + "§eDie Lobby wurde erfolgreich erstellt!");
                }
            } else if (args.length == 2) {

                if(args[0].equalsIgnoreCase("save")) {
                    name = args[1];
                    int index = mapExists(name, maps);

                    if (index == -1) {
                        player.sendMessage(Main.prefix + "§cDiese Map ist nicht vorhanden!");
                        return false;
                    }

                    List<Map<String, Object>> locs = new ArrayList<>();

                    locations.forEach(location -> {
                        Map<String, Object> loc = new HashMap<>();
                        loc.put("x", location.getX());
                        loc.put("y", location.getY());
                        loc.put("z", location.getZ());
                        loc.put("pitch", location.getPitch());
                        loc.put("yaw", location.getYaw());
                        loc.put("world", player.getWorld().getName());
                        locs.add(loc);
                    });

                    int MaxTeamCount = (Integer) maps.get(index).get(Path.MaxTeamCount.toString());

                    if (locations.size() == MaxTeamCount) {
                        maps.get(index).put(Path.Locations.toString(), locs);
                        Main.yamlConfigurationSkyWars.set("maps", maps);
                        try {
                            Main.yamlConfigurationSkyWars.save(Main.fileSkyWars);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        locations.clear();
                        player.sendMessage(Main.prefix + "§eDie Locations wurden für die Map: " + name + " erfolgreich gespeichert!");
                        Main.restartCountdown.start();
                        return true;
                    } else if (locations.size() < MaxTeamCount) {
                        try {
                            int locationsSize = locations.size();
                            int MaxTeams = (Integer) maps.get(index).get(Path.MaxTeamCount.toString());
                            int result = MaxTeams - locationsSize;

                            player.sendMessage(Main.prefix + "§cDu musst noch ingsgesammt " + result + " spawns setzen!");

                        } catch (Exception e) {
                            e.printStackTrace();
                            player.sendMessage(Main.prefix + "§cDu musst noch ingsgesammt spawns setzen!");

                        }
                    } else {
                        player.sendMessage(Main.prefix + "§cACHTUNG! Es wurden zu viele Locations gesetzt benutze /clear und setze die Locations erneut!");
                    }

                    return true;
                } else {
                    player.sendMessage(Main.prefix + "§cBitte benutze net Befehl /loc save mapName");
                }
            } else {
                player.sendMessage(Main.prefix + "§cBenutze /loc <set, clear, save>");
            }
        }

        return false;
    }

    private int mapExists(String name, List<Map<String, Object>> maps) {
        for (int i = 0; i < maps.size(); i++) {
            String map = (String) maps.get(i).get(Path.MapName.toString());

            if (map.equalsIgnoreCase(name)) {
                return i;
            }
        }

        return -1;
    }
}
