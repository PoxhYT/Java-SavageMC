package de.anweisung.premiumkickapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PremiumListener implements Listener{

	@EventHandler
	public void onLogin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		int i = PremiumKick.getMaxPlayers();
		if(!(i >= Bukkit.getOnlinePlayers().size())){
			return;
		}
		if(i == Bukkit.getOnlinePlayers().size()){
			if(!p.hasPermission("server.premium")){
				e.disallow(Result.KICK_OTHER, "§cDu benötigst mindestens den §ePremium §cRang, um diesen Server betreten zu können!");
			}
			if(Main.PremiumKick == false){
				p.sendMessage(Data.Prefix + "§cDiese Runde hat schon längst begonnen! Bitte gedulde dich etwas!");
				return;
			}
			if(Main.PremiumKick == true){
				if(p.hasPermission("server.premium")){
					int q = 0;
					for(Player all : Bukkit.getOnlinePlayers()){
						
						if(all.hasPermission("server.premium")){
							q++;
							e.disallow(Result.KICK_OTHER, "§cDieser Server ist komplett voll. Jeder Spieler hat einen §6Premium §cRang!");
						if(q == Bukkit.getOnlinePlayers().size()){
							return;
						}
						}
					}
					for(Player all : Bukkit.getOnlinePlayers()){
						if(!all.hasPermission("server.premium")){
							
							all.kickPlayer("§cDu wurdest von einem §ePremium §cSpieler gekickt! §3https://shop.savagemc.net/rang");
							e.allow();
							return;
						}
					}
					
				}else{
					p.sendMessage(Data.Prefix + "");
					p.sendMessage(Data.Prefix + "§3https://shop.savagemc.net/rang");
					p.sendMessage(Data.Prefix + "");
				}
			}
		}
	}
}
