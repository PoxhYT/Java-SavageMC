package de.mlgrush.maps;

import de.mlgrush.enums.LocationType;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LocationHandler {

    private final String FILE_NAME = "location_values.yml";
    private final String FOLDER_NAME = "plugins//MLGRush";

    public HashMap<String, Location> locations = new HashMap<>();

    public void loadFile() {
        final File file = new File(FOLDER_NAME, FILE_NAME);
        if (!file.exists()) {
            final File folder = new File(FOLDER_NAME);
            if(!folder.exists())
                folder.mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addLocation(final String name, final Location location) {
        final File file = new File(FOLDER_NAME, FILE_NAME);
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        locations.put(name.toUpperCase(), location);

        final List<String> names = configuration.getStringList("LocationNames");

        if (!names.contains(name)) {
            names.add(name);
            configuration.set("LocationNames", names);
        }

        configuration.set("Locations." + name, location);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getLocationByFile(final String name) {
        final File file = new File(FOLDER_NAME, FILE_NAME);
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        return (Location) configuration.get("Locations." + name);
    }

    public Location getLocationByFile(final LocationType locationType) {
        final File file = new File(FOLDER_NAME, FILE_NAME);
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        return (Location) configuration.get("Locations." + locationType.toString());
    }

    public void loadLocations() {
        final File file = new File(FOLDER_NAME, FILE_NAME);
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final List<String> list = configuration.getStringList("LocationNames");

        int size = 0;

        // Iterates all locations in the list and adds them to the HashMap
        for (String current : list) {
            locations.put(current, this.getLocationByFile(current));
            System.out.println("[MLGRUSH] Location " + current + " loaded successfully.");
            size++;
        }

        System.out.println("[MLGRUSH] " + size + " locations are loaded!");

    }

    public Location getLocationFromCache(final LocationType locationType) {
        return locations.get(locationType.toString());
    }

}


