package de.lobby.listener;

import de.lobby.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onWriting(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (Main.SilentLobby == true) {
            event.setCancelled(true);
            player.sendMessage(Main.silenthub + "§r§7Du kannst in der §r§4SilentLobby §r§7nicht schreiben");
        }

        String msg = event.getMessage();
        event.setFormat(player.getDisplayName() + " §8➔ §r§7" + msg);
    }
}
