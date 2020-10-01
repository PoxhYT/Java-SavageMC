package de.mlgrush.listener;

import de.mlgrush.enums.GameState;
import de.mlgrush.gamestates.GameStateHandler;
import de.mlgrush.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().replace("%", "%%");
        if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
            if (Main.getInstance().getTeamHandler().hasTeam(player)) {
                event.setFormat("§9Blau " + Main.getInstance().getTeamHandler().getPlayerTeam(player).toString().replace("TEAM_", "") + "&8❘ §7" + player.getName() + " §8➜ §7" + message);
            } else {
                event.setFormat("§7 " + player.getName() + " §8➜ §7" + message);
            }
        } else if (Main.getInstance().getTeamHandler().hasTeam(player)) {
            event.setFormat("§cRot " + Main.getInstance().getTeamHandler().getPlayerTeam(player).toString().replace("TEAM_", "") + "&8❘ §7" + player.getName() + " §8➜ §7" + message);
        } else if (Main.getInstance().getTeamHandler().isSpectator(player)) {
            event.setCancelled(true);
            player.sendMessage(Main.prefix + "Als Zuschauer kannst du nicht schreiben.");
        }
    }
}
