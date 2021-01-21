package de.poxh.premiumkick.listener;

import de.poxh.premiumkick.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PremiumKick implements Listener {

    private Main instance;

    public PremiumKick(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        Player p = e.getPlayer();
        int i = Bukkit.getMaxPlayers();
        if(!(i >= Bukkit.getOnlinePlayers().size())){
            return;
        }
        if(i == Bukkit.getOnlinePlayers().size()){
            if(!p.hasPermission("server.premium")){
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Main.prefix + "Du benötigst mindestens den §6§lPremium§7-§7§lRang§7, um volle §b§lRunden §7betreten zu können.");
            }
            if(instance.premiumKick == false){
                p.sendMessage(Main.prefix + "§cDiese Runde hat schon längst begonnen! Bitte gedulde dich etwas!");
                return;
            }
            if(instance.premiumKick == true){
                if(p.hasPermission("server.premium")){
                    int q = 0;
                    for(Player all : Bukkit.getOnlinePlayers()){

                        if(all.hasPermission("server.premium")){
                            q++;
                            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cDieser Server ist komplett voll. Jeder Spieler hat einen §6§lPremium§7-§7§lRang§7");
                            if(q == Bukkit.getOnlinePlayers().size()){
                                return;
                            }
                        }
                    }
                    for(Player all : Bukkit.getOnlinePlayers()){
                        if(!all.hasPermission("server.premium")){
                            all.kickPlayer("§cDu wurdest von einem §6Premium §cSpieler gekickt!");
                            e.allow();
                            return;
                        }
                    }
                }
            }
        }
    }
}