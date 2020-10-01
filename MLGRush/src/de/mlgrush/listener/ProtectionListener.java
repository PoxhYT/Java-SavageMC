package de.mlgrush.listener;

import de.mlgrush.enums.GameState;
import de.mlgrush.gamestates.GameStateHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ProtectionListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!GameStateHandler.isAllowMove())
            player.teleport(player.getLocation());
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}

