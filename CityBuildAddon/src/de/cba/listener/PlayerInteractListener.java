package de.cba.listener;

import de.cba.main.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private HashMap<UUID, Integer> countBlocks = new HashMap<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(event.getBlock().getType() == Material.SANDSTONE) {
            countBlocks.put(player.getUniqueId(), +1);
            player.sendMessage(Main.prefix + "Du hast bisher §e" + countBlocks.get(player.getUniqueId() + " Blöcke §7platziert!"));
        }
    }
}
