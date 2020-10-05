package de.pxav.mlgrush.listener;

// The project Stream - MLGRush is developed and updated by PXAV.
// You are not allowed to claim this as your own, give it to 
// others or even sell it.
//
// Contact me on:
// YouTube: https://www.youtube.com/channel/UCtXSAGTwurKVeEbwEKMlFog
// Twitter: https://twitter.com/OrigPXAV
// 
// (c) 2018 PXAV     

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.enums.GameState;
import de.pxav.mlgrush.gamestates.GameStateHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {

        final Player player = event.getPlayer();
        final String message = event.getMessage().replace("%", "%%");

        if (GameStateHandler.getGameState() == GameState.LOBBY || GameStateHandler.getGameState() == GameState.ENDING) {
            if(MLGRush.getInstance().getTeamHandler().hasTeam(player)) {
                event.setFormat("§a§lTEAM #" + MLGRush.getInstance().getTeamHandler().getPlayerTeam(player).toString().replace("TEAM_", "") + " §8● §7" + player.getName() + " §8➜ §7" + message);
            } else {
                event.setFormat("§7" + player.getName() + " §8➜ §7" + message);
            }
        } else {
            if (MLGRush.getInstance().getTeamHandler().hasTeam(player)) {
                event.setFormat("§a§lTEAM #" + MLGRush.getInstance().getTeamHandler().getPlayerTeam(player).toString().replace("TEAM_", "") + " §8● §7" + player.getName() + " §8➜ §7" + message);
            } else if (MLGRush.getInstance().getTeamHandler().isSpectator(player)) {
                event.setCancelled(true);
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§cAls Zuschauer kannst du nicht schreiben.");
            }
        }
    }

}
