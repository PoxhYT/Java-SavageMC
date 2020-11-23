package de.sw.commands;

import com.google.gson.Gson;
import com.rosemite.services.helper.Log;
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<Map<String, Object>> maps = (List<Map<String, Object>>) Main.yamlConfigurationSkyWars.getList("maps");

        Player player = (Player) sender;
        if (sender instanceof Player) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("set")) {
                    player.sendMessage(Main.prefix + "§eLocation wurde gesetzt!");
                    locations.add(player.getLocation());
                } else if(args[0].equalsIgnoreCase("clear")) {
                    locations.clear();
                    player.sendMessage(Main.prefix + "§cDie Location List wurde erfolgreich gecleart!");
                }
            } else if (args.length == 2) {
                if(args[0].equalsIgnoreCase("save")) {
                    String name = args[1];

                    for (int i = 0; i < maps.size(); i++) {
                        if (name.equalsIgnoreCase((String) maps.get(i).get(Path.MapName.toString()))) {
                            List<Map<String, Object>> locs = new ArrayList<>();

                            locations.forEach(location -> {
                                Map<String, Object> loc = new HashMap<>();
                                loc.put("x", location.getX());
                                loc.put("y", location.getY());
                                loc.put("z", location.getZ());
                                loc.put("pitch", location.getPitch());
                                loc.put("yaw", location.getYaw());
                                loc.put("world", "world");
                                locs.add(loc);
                            });

                            maps.get(i).put(Path.Locations.toString(), locs);
                            Main.yamlConfigurationSkyWars.set("maps", maps);
                            try {
                                Main.yamlConfigurationSkyWars.save(Main.fileSkyWars);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            locations.clear();

                            player.sendMessage(Main.prefix + "§eDie Locations wurden erfolgreich gespeichert!");
                            return true;
                        }
                    }
                }
            } else {
                player.sendMessage(Main.prefix + "§cBenutze /loc <set, clear, save>");
            }
        }

        return false;
    }
}
