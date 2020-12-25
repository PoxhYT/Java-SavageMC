package de.poxh.services.main;

import com.mongodb.client.MongoDatabase;
import de.poxh.services.services.lobby.LobbyService;
import de.poxh.services.services.player.PlayerService;

public class ServiceHolder {

    private final PlayerService playerService;
    private final LobbyService lobbyService;

    public ServiceHolder(MongoDatabase database, MainService service) {
        this.playerService = new PlayerService(database, service);
        this.lobbyService = new LobbyService(database, service);
    }

    public LobbyService getLobbyService() {
        return lobbyService;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }
}
