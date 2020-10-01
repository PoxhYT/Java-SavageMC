package de.lobby.listener;

import de.lobby.inventories.LobbyInventory;
import de.lobby.main.Main;
import de.lobby.utils.SBManager;
import de.lobby.utils.SpawnFile;
import de.magnus.coinsapi.util.CoinsAPI;
import de.ticketapi.util.TicketAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        String uuid = String.valueOf(player.getUniqueId());
        CoinsAPI.createPlayer(uuid);
        TicketAPI.createPlayer(uuid);

        if (Main.build.contains(player)) {
            Main.build.remove(player);
        }
        try {
            player.teleport(SpawnFile.getSpawn("Spawn"));
        } catch (Exception ex) {
            player.sendMessage("");
            player.sendMessage(Main.prefix + "§r§7Der §r§eLobbySpawn §r§7wurde noch §r§cnicht §r§7gesetzt!");
            player.sendMessage(Main.prefix + "§r§cSetze den Spawn mit §7: /§7§rsetspawn");
            player.sendMessage("");
        }
        event.setJoinMessage("");
        if (Main.SilentLobby == true) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.hidePlayer(player);
                player.hidePlayer(all);

            }
            player.sendMessage(Main.prefix + "§r§7du befindest dich nun in der §r§cSilentLobby§7.");
        }

        if (Main.SilentLobby == true) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.hidePlayer(player);
                player.hidePlayer(all);
            }
        }

        player.getInventory().clear();
        LobbyInventory.setTeamInventory(player);
        event.setJoinMessage(Main.prefix + player.getDisplayName() + " §r§7hat den §r§bServer §r§abetreten§r§7.");
        player.setGameMode(GameMode.SURVIVAL);
        player.setFoodLevel(20);
        SBManager.setScoreboard(player);
        Main.scoreCD();
    }
}
