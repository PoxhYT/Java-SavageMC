package de.sw.utils;

import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player death = event.getEntity();
        Player killer = death.getKiller();
        Data.alive.remove(death);
        Bukkit.broadcastMessage(Main.prefix + "§e" + death.getName() + " §7ist gestorben, verbleibende §eSpieler§7: §a" + Data.alive.size());
    }
}
