package com.rosemite.server.listener;

import com.rosemite.models.reward.RewardInfo;
import com.rosemite.models.service.common.IService;
import com.rosemite.models.service.player.PlayerInfo;
import com.rosemite.services.player.PlayerService;
import com.rosemite.services.reward.RewardService;
import com.rosemite.services.skywars.SkywarsService;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLoginListener implements Listener {

    private final PlayerService playerService;
    private final SkywarsService skywarsService;
    private final RewardService rewardService;
    private final IService iService;

    public PlayerLoginListener(PlayerService playerService, SkywarsService skywarsService, RewardService rewardService, IService iService) {
        this.playerService = playerService;
        this.skywarsService = skywarsService;
        this.rewardService = rewardService;
        this.iService = iService;
    }


    @EventHandler
    public void onLogin(PostLoginEvent event) {
        ProxiedPlayer proxiedPlayer = event.getPlayer();
        PlayerInfo playerInfo = playerService.getPlayerInfo(proxiedPlayer.getUniqueId().toString());
        RewardInfo rewardInfo = rewardService.getPlayerInfo(proxiedPlayer.getUniqueId().toString());

        if (playerInfo == null) {
            createNewPlayer(proxiedPlayer.getUniqueId().toString(), proxiedPlayer.getDisplayName());
        }

        if(rewardInfo == null) {
            createNewRewardInfo(proxiedPlayer.getUniqueId().toString(), proxiedPlayer.getDisplayName());
        }
    }

    private PlayerInfo createNewPlayer(String uuid, String displayName) {
        // Add new Player to Database
        PlayerInfo playerInfo =  playerService.createNewPlayer(uuid, displayName);

        // Initialize Kits
        this.skywarsService.initializeKits(uuid);
        iService.getSoupTrainingService().initializePlayer(playerInfo);

        return playerInfo;
    }

    private RewardInfo createNewRewardInfo(String uuid, String displayName) {
        RewardInfo rewardInfo = rewardService.createNewPlayer(uuid, displayName);
        return rewardInfo;
    }
}
