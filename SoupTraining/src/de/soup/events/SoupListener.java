package de.soup.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.ConcurrentHashMap;

public class SoupListener implements Listener {

    public static ConcurrentHashMap<String, Integer[]> droppedItems = (ConcurrentHashMap)new ConcurrentHashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getPlayer() == null)
            return;
        if(event.getItem() != null)
            if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
    }
}
