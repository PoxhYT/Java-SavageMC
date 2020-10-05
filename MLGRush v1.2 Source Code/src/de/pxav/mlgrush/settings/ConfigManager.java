package de.pxav.mlgrush.settings;

// The project Stream - MLGRush is developed and updated by PXAV.
// You are not allowed to claim this as your own, give it to 
// others or even sell it.
//
// Contact me on:
// YouTube: https://www.youtube.com/channel/UCtXSAGTwurKVeEbwEKMlFog
// Twitter: https://twitter.com/OrigPXAV
// 
// (c) 2018 PXAV     

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private boolean useChatFormat;
    private int maxPoints;
    private boolean cacheLoader;
    private boolean damageCheck;

    private String mapName;
    private String mapBuilder;

    public void loadFile() {
        final File file = new File("plugins//MLGRush", "config.yml");
        if(!file.exists()) {
            final File folder = new File("plugins//MLGRush");
            if(!folder.exists()) folder.mkdirs();
            try {
                file.createNewFile();
                this.writeDefault();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeDefault() throws IOException {
        final File file = new File("plugins//MLGRush", "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        configuration.set("Settings.FormatChat", true);
        configuration.set("Settings.CacheLoader", false);
        configuration.set("Settings.DamageCheck", false);
        configuration.set("Settings.WinPoints", 3);
        configuration.set("Map.Name", "YourName");
        configuration.set("Map.Builder", "A Builder");
        configuration.save(file);
    }

    public void loadSettings() {
        final File file = new File("plugins//MLGRush", "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        setMaxPoints(configuration.getInt("Settings.WinPoints"));
        setUseChatFormat(configuration.getBoolean("Settings.FormatChat"));
        setCacheLoader(configuration.getBoolean("Settings.CacheLoader"));
        setDamageCheck(configuration.getBoolean("Settings.DamageCheck"));
        setMapName(configuration.getString("Map.Name"));
        setMapBuilder(configuration.getString("Map.Builder"));
    }


    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public boolean isUseChatFormat() {
        return useChatFormat;
    }

    public void setUseChatFormat(boolean useChatFormat) {
        this.useChatFormat = useChatFormat;
    }

    public boolean isCacheLoader() {
        return cacheLoader;
    }

    public void setCacheLoader(boolean cacheLoader) {
        this.cacheLoader = cacheLoader;
    }

    public boolean isDamageCheck() {
        return damageCheck;
    }

    public void setDamageCheck(boolean damageCheck) {
        this.damageCheck = damageCheck;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapBuilder() {
        return mapBuilder;
    }

    public void setMapBuilder(String mapBuilder) {
        this.mapBuilder = mapBuilder;
    }
}
