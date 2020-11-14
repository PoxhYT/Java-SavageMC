package main.java.de.xenodev.cbs.utils;

import java.io.File;
import java.io.IOException;

import main.java.de.xenodev.cbs.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	
	public static File getConfigFile() {
		return new File("plugins//" + Main.getInstance().getName(), "config.yml");
	}
	
	public static FileConfiguration getConfigFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getConfigFile());
	}
	
	public static void setConfig() {
		FileConfiguration cfg = getConfigFileConfiguration();

		cfg.options().copyDefaults(true);
		cfg.addDefault("Prefix", "§0[§3§lPrefix§0]");

		try {
			cfg.save(getConfigFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Main.getInstance().prefix = cfg.getString("Prefix");
	}
}
