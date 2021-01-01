package de.poxh.lobby.listener;

import com.rosemite.models.service.player.PlayerInfo;
import de.poxh.lobby.api.LocationAPI;
import de.poxh.lobby.main.Main;
import de.poxh.lobby.manager.EffectsManager;
import de.poxh.lobby.manager.InventoryManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinListener implements Listener {

    private LuckPerms luckPerms;

    public PlayerJoinListener(LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(LocationAPI.getSpawn("Lobby"));
        Main.sbManager.setLobbyBoard(player);

        //Getting players prefix
        CachedMetaData metaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        String prefix = metaData.getPrefix();

        String serverName = player.getServer().getServerName();
        Main.service.getPlayerService().setPlayerServer(player.getUniqueId().toString(), serverName);

        if(prefix == null) {
            event.setJoinMessage(Main.prefix + "§7Spieler" + " §7❘ " + player.getName() + " §7hat den Server betreten!");
        } else {
            event.setJoinMessage(Main.prefix + prefix + " §7❘ " + player.getName() + " §7hat den Server betreten!");
            InventoryManager.setLobbyInventory(player);
            EffectsManager.createFireWork(player);
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        CachedMetaData metaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        String prefix = metaData.getPrefix();

        if(prefix == null) {

        }
    }
}
