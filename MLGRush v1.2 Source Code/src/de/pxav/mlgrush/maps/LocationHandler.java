package de.pxav.mlgrush.maps;

import de.pxav.mlgrush.enums.LocationType;
import org.bukkit.Bukkit;
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

    /**
     * Loads the YAML {@link File} for the locations called
     * '{@code default_location_values.yml}' and
     * creates the folder if necessary.
     */
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

    /**
     * Adds a location to the file and to the cache.
     * @param name The name of the location under which you can access to the location later.
     * @param location The location where the players should be teleported.
     */
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

    /**
     * Gets a {@link Location} object from the file
     * and returns it.
     * @param name The name of the location that should be loaded.
     * @return The location object
     */
    public Location getLocationByFile(final String name) {
        final File file = new File(FOLDER_NAME, FILE_NAME);
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        return (Location) configuration.get("Locations." + name);
    }

    /**
     * Gets a {@link Location} object from the file
     * and returns it.
     * @param locationType The name of the location that should be loaded.
     * @return The location object
     */
    public Location getLocationByFile(final LocationType locationType) {
        final File file = new File(FOLDER_NAME, FILE_NAME);
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        return (Location) configuration.get("Locations." + locationType.toString());
    }

    /**
     * Loads all locations from the config file.
     */
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

    /**
     * Gets a location from the HashMap.
     * @param locationType The type of the location you would like to get.
     * @return The final location from the {@code HashMap}.
     */
    public Location getLocationFromCache(final LocationType locationType) {
        return locations.get(locationType.toString());
    }

}
