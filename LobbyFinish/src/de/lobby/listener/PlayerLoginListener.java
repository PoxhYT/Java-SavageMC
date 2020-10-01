package de.lobby.listener;

import de.lobby.main.Main;
import de.lobby.mysql.PremiumLobbyAutoConnect;
import de.lobby.mysql.SilentLobbyAutoConnect;
import de.lobby.utils.SpigotProxyAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerLoginListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (Main.SilentLobby != true) {
            Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin) Main.getInstance(), new Runnable() {
                public void run() {
                    if (PremiumLobbyAutoConnect.getSetting(p.getUniqueId().toString()).intValue() == 1) {
                        try {
                            SpigotProxyAPI.sendPlayer(p, "PremiumLobby-1");
                        } catch (Exception ex) {
                            p.sendMessage("ist ein Fehler aufgetreten");
                        }
                    } else if (SilentLobbyAutoConnect.getSetting(p.getUniqueId().toString()).intValue() == 1) {
                        try {
                            SpigotProxyAPI.sendPlayer(p, "SilentLobby-1");
                        } catch (Exception ex) {
                            p.sendMessage("ist ein Fehler aufgetreten");
                        }
                    }
                }
            }, 1L);
        }
    }
}
