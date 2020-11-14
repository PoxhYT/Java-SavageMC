package main.java.de.xenodev.cbs.listener;

import main.java.de.xenodev.cbs.utils.TitleManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();

        for(Player all : Bukkit.getOnlinePlayers()){
            TitleManager.setTitle(all, 20, 50, 20, "§e" + p.getName(), "§7hat den Server §cverlassen");
        }

        e.setQuitMessage("");
    }

}
