package de.anweisung.premiumkickapi;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	
	public static boolean PremiumKick;
	public void onEnable(){
		File file = new File("plugins//PremiumKick//config.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		if(cfg.get("MaxPlayers") == null){
			cfg.set("MaxPlayers", 100);
			try {
				cfg.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		getCommand("setmaxplayers").setExecutor(new Commands());
		Bukkit.getPluginManager().registerEvents(new PremiumListener(), this);
		
	}
}
