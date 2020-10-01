package de.anweisung.premiumkickapi;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class PremiumKick {

	public static void allowPremiumKick(){
		Main.PremiumKick = true;
	}
	public static void disallowPremiumKick(){
		Main.PremiumKick = false;
	}
	public static int getMaxPlayers(){
		File file = new File("plugins//PremiumKick//config.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		int i = cfg.getInt("MaxPlayers");
		return i;
	}
}
