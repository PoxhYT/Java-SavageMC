package de.poxh.services.main;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import de.poxh.services.listener.PlayerJoinEvent;
import de.poxh.services.models.lobby.LobbyInfo;
import de.poxh.services.services.coin.CoinService;
import de.poxh.services.services.lobby.LobbyService;
import de.poxh.services.services.player.PlayerService;
import de.poxh.services.services.report.ReportService;
import de.poxh.services.services.skywars.SkyWarsService;
import de.poxh.services.services.ticket.TicketService;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainService extends JavaPlugin {

    private ServiceHolder holder;

    public static String prefix = "§8[§eServices§8] §e";

    private static MongoClient mongoClient = MongoClients.create("mongodb+srv://admin:aynea07aOkiaactF@cluster0.u4x7t.mongodb.net/SavageMC?retryWrites=true&w=majority");

    @Override
    public void onEnable() {
        //Connecting to database
        MongoClient mongoClient = MongoClients.create("mongodb+srv://admin:aynea07aOkiaactF@cluster0.u4x7t.mongodb.net/SavageMC?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("SavageMC");
        Bukkit.getConsoleSender().sendMessage(prefix + "Successfully loaded the services!");

        registerEvents();

        //Initialize Services
        holder = new ServiceHolder(database, this);

        //Creating LobbyInfo
        LobbyInfo lobbyInfo = holder.getLobbyService().getLobbyInfo();
        if(lobbyInfo == null) {
            createLobbyInfo();
            Bukkit.getConsoleSender().sendMessage(prefix + "Creating new LobbyInfos in the database...");
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + "LobbyInfos already exists!");
        }
    }

    public static MainService getService(MainService service) {
        JavaPlugin servicePlugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("Services");
        if (servicePlugin != null && service == null) {
            return (MainService) servicePlugin;
        }

        return service;
    }

    private void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new PlayerJoinEvent(new PlayerService(mongoClient.getDatabase("SavageMC"),this)),this);
    }

    public PlayerService getPlayerService() {
        return new PlayerService(mongoClient.getDatabase("SavageMC"), this);
    }

    public CoinService getCoinService() {
        return new CoinService(mongoClient.getDatabase("SavageMC"), this);
    }

    public TicketService getTicketService() {
        return new TicketService(mongoClient.getDatabase("SavageMC"), this);
    }

    public LobbyService getLobbyService() {
        return holder.getLobbyService();
    }

    public SkyWarsService getSkyWarsService() {
        return new SkyWarsService(mongoClient.getDatabase("SavageMC"), this);
    }

    public ReportService getReportService() {
        return new ReportService(mongoClient.getDatabase("SavageMC"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private LobbyInfo createLobbyInfo() {
        LobbyInfo lobbyInfo = holder.getLobbyService().createLobbyInfo();
        return lobbyInfo;
    }
}
