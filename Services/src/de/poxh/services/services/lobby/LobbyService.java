package de.poxh.services.services.lobby;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import de.poxh.services.main.MainService;
import de.poxh.services.models.common.Paths;
import de.poxh.services.models.lobby.LobbyInfo;
import de.poxh.services.models.player.PlayerInfo;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.Map;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class LobbyService {
    private MongoDatabase db;

    public LobbyService(MongoDatabase db, MainService mainService) {
        this.db = db;
    }

    public LobbyInfo createLobbyInfo() {
        LobbyInfo lobbyInfo = new LobbyInfo(
            false,
            false,
            Bukkit.getOnlinePlayers().size() -1

        );

        String json = new Gson().toJson(lobbyInfo, LobbyInfo.class);

        Map<String, Object> doc = new Gson().fromJson(json, Map.class);
        db.getCollection(Paths.LobbyInfo.toString()).insertOne(new Document(doc));

        return lobbyInfo;
    }

    public LobbyInfo getLobbyInfo() {
        Document doc = db.getCollection(Paths.LobbyInfo.toString()).find(Filters.eq("spawnsSet", false)).first();
        if (doc == null) {
            return null;
        }

        return new Gson().fromJson(doc.toJson(), LobbyInfo.class);
    }

    public void setOnlinePlayers(int onlinePlayers) {

        db.getCollection(Paths.LobbyInfo.toString()).updateOne((Filters.eq("ref", "HALLO")),
                combine(set("onlinePlayers", onlinePlayers)));

    }

    public void setVillager(boolean villager) {
        db.getCollection(Paths.LobbyInfo.toString()).updateOne((Filters.eq("ref", "HALLO")),
                combine(set("villagerSet", villager)));
    }

    public boolean getVillager() {
        Document document = db.getCollection(Paths.LobbyInfo.toString()).find().first();
        return (Boolean) document.get("villagerSet");
    }
}