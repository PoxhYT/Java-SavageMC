package de.mlgrush.listener;

import de.mlgrush.api.SQLStats;
import de.mlgrush.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.DecimalFormat;

public class Stats implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SQLStats.createPlayer(player.getUniqueId().toString());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.getKiller() instanceof Player) {
            SQLStats.addDeaths(player.getUniqueId().toString(), 1);
            SQLStats.addKills(player.getKiller().getUniqueId().toString(), 1);
        } else {
            SQLStats.addDeaths(player.getUniqueId().toString(), 1);
        }
    }
}
