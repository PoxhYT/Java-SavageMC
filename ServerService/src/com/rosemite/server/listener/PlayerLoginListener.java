package com.rosemite.server.listener;

import com.rosemite.helper.Log;
import com.rosemite.models.citybuild.CityBuildInfos;
import com.rosemite.models.reward.RewardInfo;
import com.rosemite.models.service.common.IService;
import com.rosemite.models.service.player.PlayerInfo;
import com.rosemite.services.citybuild.CityBuildService;
import com.rosemite.services.friends.FriendsService;
import com.rosemite.services.player.PlayerService;
import com.rosemite.services.reward.RewardService;
import com.rosemite.services.skywars.SkywarsService;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Date;

public class PlayerLoginListener implements Listener {
    private final PlayerService playerService;
    private final SkywarsService skywarsService;
    private final RewardService rewardService;
    private final FriendsService friendsService;
    private final CityBuildService cityBuildService;
    private final IService iService;

    public PlayerLoginListener(IService iService) {
        this.playerService = iService.getPlayerService();
        this.skywarsService = iService.getSkywarsService();
        this.rewardService = iService.getRewardService();
        this.friendsService = iService.getFriendsService();
        this.cityBuildService =  iService.getCityBuildService();
        this.iService = iService;
    }


    @EventHandler
    public void onLogin(PostLoginEvent event) {
        ProxiedPlayer proxiedPlayer = event.getPlayer();
        String uuid = proxiedPlayer.getUniqueId().toString();
        String name = proxiedPlayer.getName();
        String displayName = proxiedPlayer.getDisplayName();

        PlayerInfo playerInfo = playerService.getPlayerInfo(uuid);

        if (playerInfo == null) {
            createNewPlayer(uuid, name);
            createNewRewardInfo(uuid, name);
        } else {
            playerService.onJoin(uuid);
        }

        CityBuildInfos cityBuildInfos = cityBuildService.getPlayerInfo(uuid);

        if(cityBuildInfos == null) {
            createUser(uuid, name, displayName);
        }
    }

    @EventHandler
    public void onProxyLeave(final PlayerDisconnectEvent p) {
        Log.d("The Player: " + p.getPlayer().getName() + " left the Server.");
        playerService.onLeave(p.getPlayer().getUniqueId().toString());
    }

    private PlayerInfo createNewPlayer(String uuid, String displayName) {
        // Add new Player to Database
        PlayerInfo playerInfo = playerService.createNewPlayer(uuid, displayName);

        // Initialize SkyWars Kits
        this.skywarsService.initializeKits(uuid);

        // Initialize Soup Training Stats
        iService.getSoupTrainingService().initializePlayer(playerInfo);

        // Initialize Relationships Document
        this.friendsService.initializeRelation(uuid, displayName);

        return playerInfo;
    }

    private CityBuildInfos createUser(String uuid, String playerName, String displayName) {
        CityBuildInfos cityBuildInfos = cityBuildService.createNewPlayer(uuid, playerName, displayName);
        return cityBuildInfos;
    }

    private RewardInfo createNewRewardInfo(String uuid, String displayName) {
        RewardInfo rewardInfo = rewardService.createNewPlayer(uuid, displayName);
        return rewardInfo;
    }
}
