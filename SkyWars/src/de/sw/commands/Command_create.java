package de.sw.commands;

import com.google.gson.Gson;
import com.rosemite.services.helper.Log;
import de.sw.enums.Path;
import de.sw.main.Main;
import de.sw.manager.GameManager;
import de.sw.manager.SkyWarsMapData;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
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
            player.sendMessage(Main.prefix + "Bitte benutze den Befehl /createwolrd <name>");
        } else if(args.length == 1) {
            String name = args[0];

            // Get Maps
            List<Map<String, Object>> maps = (List<Map<String, Object>>) Main.yamlConfigurationSkyWars.getList("maps");

            for (int i = 0; i < maps.size(); i++) {
                // Check if map name already exists. If so we exit & return. Else we save the map
                if (maps.get(i).get(Path.MapName.toString()) == name) {
                    player.sendMessage(Main.prefix + "§cDie Map wurde bereits erstellt!");
                    return false;
                }
                Log.d(maps.get(i).get(Path.MapName.toString()));
            }

            // Save map
            SkyWarsMapData data = new SkyWarsMapData(
                    name,
                    "8x1",
                    null,
                    1,
                    1,
                    true
            );

            // Convert SkyWarsMapData into Map
            Gson g = new Gson();
            Map<String, Object> tempMap = g.fromJson(g.toJson(data), Map.class);
            Map<String, Object> skywarsMpData = new HashMap<>();
            Map<String, Object> mapData = new HashMap<>();

            tempMap.forEach((k, v) -> {
                Log.d(k);
                String key = k.substring(0, 1).toUpperCase() + k.substring(1);
                skywarsMpData.put(key, v);
            });

            for (Map.Entry<String, Object> entry : skywarsMpData.entrySet()) {
                mapData.put(entry.getKey(), entry.getValue());
            }

            // Add Map to list
            maps.add(mapData);
            Main.yamlConfigurationSkyWars.addDefault("maps", maps);
            try {
                Main.yamlConfigurationSkyWars.save(Main.fileSkyWars);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameManager.games.add(name);
            player.sendMessage(Main.prefix + "Du hast die Map §e" + name + " erfolgreich erstellt!");
        }
        return false;
    }

    private void createWorlds() {
        WorldCreator wc = new WorldCreator("TEST");

        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.FLAT);
        wc.generatorSettings("0;0;0;");
        wc.createWorld();
    }
}

