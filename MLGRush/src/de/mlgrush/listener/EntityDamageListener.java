package de.mlgrush.listener;

import de.mlgrush.enums.GameState;
import de.mlgrush.gamestates.GameStateHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {

            if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
                event.setCancelled(true);
            } else if(GameStateHandler.getGameState() == GameState.INGAME) {
                if(event.getCause() == EntityDamageEvent.DamageCause.FALL)
                    event.setCancelled(true);
            }

        }
    }

}

