package com.rosemite.server.main;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.rosemite.helper.Log;
import com.rosemite.models.service.common.IService;
import com.rosemite.server.config.Config;
import com.rosemite.server.listener.PlayerLoginListener;
import com.rosemite.services.coin.CoinService;
import com.rosemite.services.friends.FriendsService;
import com.rosemite.services.holder.ServiceHolder;
import com.rosemite.services.lobby.LobbyService;
import com.rosemite.services.player.PlayerService;
import com.rosemite.services.report.ReportService;
import com.rosemite.services.reward.RewardService;
import com.rosemite.services.skywars.SkywarsService;
import com.rosemite.services.souptraining.SoupTrainingService;
import com.rosemite.services.ticket.TicketService;
import com.sun.istack.internal.NotNull;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class ServerService extends Plugin implements IService {
    private ServiceHolder holder;

    @Override
    public void onEnable() {
        super.onEnable();
        String connectionString = Config.getConnectionString();

        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase db = mongoClient.getDatabase("savagemc");

        // Initialize Services
        holder = new ServiceHolder(db,this);

        registerEvents();

        Log.d("Loaded Server Services Successfully");
    }

    private void registerEvents() {
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.registerListener(this, new PlayerLoginListener(
                holder.getPlayerService(),
                holder.getSkywarsService(),
                holder.getRewardService(),
                this));
    }

    @Override
    public SoupTrainingService getSoupTrainingService() {
        return holder.getSoupTrainingService();
    }

    @Override
    public SkywarsService getSkywarsService() {
        return holder.getSkywarsService();
    }

    @Override
    public TicketService getTicketService() {
        return holder.getTicketService();
    }

    @Override
    public CoinService getCoinService() {
        return holder.getCoinService();
    }

    @Override
    public LobbyService getLobbyService() {
        return holder.getLobbyService();
    }

    @Override
    public FriendsService getFriendsService() {
        return holder.getFriendsService();
    }

    @Override
    public RewardService getRewardService() {
        return holder.getRewardService();
    }

    @Override
    public ReportService getReportService() {
        return holder.getReportService();
    }

    @Override
    public PlayerService getPlayerService() {
        return holder.getPlayerService();
    }

    public static IService getService(IService service) {
        Plugin servicePlugin = ProxyServer.getInstance().getPluginManager().getPlugin("ServerServices");
        if (servicePlugin != null && service == null) {
            return (ServerService) servicePlugin;
        }

        return null;
    }
}
