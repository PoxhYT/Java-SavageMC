package de.sw.map;

import de.sw.enums.Path;
import de.sw.main.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Map {
    private Location[] spawnLocations = new Location[(int) Main.MapName1.get(Path.MaxTeamCount.toString())];

    public void setSpawnLocations(int spawnNumber, Location location) {
        spawnLocations[spawnNumber -1] = location;
    }
}
