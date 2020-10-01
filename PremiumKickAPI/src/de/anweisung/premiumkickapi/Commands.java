package de.anweisung.premiumkickapi;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command c, String arg2, String[] args) {
		Player p = (Player)s;
		
		if(c.getName().equalsIgnoreCase("setmaxplayers")){
					if(p.hasPermission("claymc.admin")){
						if(args.length == 0){
							p.sendMessage(Data.Prefix + "§3/SetMaxPlayers [Anzahl]");
							return true;
						}else if(args.length == 1){
						File file = new File("plugins//PremiumKick//config.yml");
						YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
						int i = Integer.parseInt(args[0]);
						cfg.set("MaxPlayers", i);
						try {
							cfg.save(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						p.sendMessage(Data.Prefix + "§3Die §cMaximale Spieleranzahl §3wurde auf §e" + i + "§3 gesetzt!");
						}else{
							p.sendMessage(Data.Prefix + "§3/SetMaxPlayers [Anzahl]");
							return true;
						}
					}else{
						p.sendMessage(Data.NoPerm);
					
				}
			
		}
	
		
		return false;
	}

	
}
