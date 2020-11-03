package de.sw.listener;

import de.sw.manager.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player d = event.getEntity();
        Player k = d.getKiller();

        if (k != null) {
            Data.roundkills.put(k, Integer.valueOf(((Integer) Data.roundkills.get(k)).intValue() + 1));
            k.sendMessage(String.valueOf(Data.roundkills.get(k)));
        }
    }
}
