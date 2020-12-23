package de.lobby.listener;


import com.rosemite.services.main.MainService;
import de.lobby.main.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

            MainService.getService(null).getLobbyService().setOnlinePlayers(Bukkit.getOnlinePlayers().size());

//            CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
//            String prefix = metaData.getPrefix();

            //Main.getInstance().scoreboardAPI.setScoreboard(player);
            //Main.scoreCD();

            Main.onlinePlayers.add(player);

//            event.setJoinMessage(prefix + " §7❘ " + player.getName());
//            instance.inventoryManager.setLobbyInventory(player);
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        MainService.getService(null).getLobbyService().setOnlinePlayers(Bukkit.getOnlinePlayers().size() -1);

    }
}
