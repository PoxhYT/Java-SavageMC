package main.java.de.xenodev.cbs.listener;

import main.java.de.xenodev.cbs.utils.ChestLottery;
import main.java.de.xenodev.cbs.utils.MoneyAPI;
import main.java.de.xenodev.cbs.utils.SBManager;
import main.java.de.xenodev.cbs.utils.TitleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();

        for(Player all : Bukkit.getOnlinePlayers()){
            TitleManager.setTitle(all, 20, 50, 20, "§e" + p.getName(), "§7hat den Server §abetreten");
        }

        MoneyAPI.createPlayer(p);
        MoneyAPI.setName(p);
        ChestLottery.createPlayer(p);
        SBManager.setScoreboard(p);

        e.setJoinMessage("");

        for(int i = 0; i < 150; i++){
            p.sendMessage("");
        }
    }
}
