package de.lobby.listener;

import de.lobby.api.LocationAPI;
import de.lobby.api.ScoreboardAPI;
import de.lobby.main.Main;
import de.magnus.coinsapi.util.CoinsAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private Main instance;
    private LuckPerms luckPerms;

    public PlayerJoinListener(Main instance, LuckPerms luckPerms) {
        this.instance = instance;
        this.luckPerms = luckPerms;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try {

            CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
            String prefix = metaData.getPrefix();

            event.setJoinMessage(prefix + " §7❘ " + player.getName());
            instance.inventoryManager.setLobbyInventory(player);
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
