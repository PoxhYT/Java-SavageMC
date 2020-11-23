package com.rosemite.services.services.player;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.services.main.MainService;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.models.player.PlayerInfo;
import com.rosemite.services.models.skywars.PlayerSkywarsKits;
import org.bson.Document;
import org.bukkit.entity.Player;
import java.util.Map;
import static com.mongodb.client.model.Filters.eq;

public class PlayerService {
    private MongoDatabase db;

    public PlayerService(MongoDatabase db, MainService mainService) {
        this.db = db;
    }

    public PlayerInfo createNewPlayer(Player player) {
        PlayerInfo playerInfo = new PlayerInfo(
            false,
            player.getDisplayName(),
            "Standard",
            player.getUniqueId().toString(),
            500,
            5
        );

        String json = new Gson().toJson(playerInfo, PlayerInfo.class);

        Map<String, Object> doc = new Gson().fromJson(json, Map.class);
        db.getCollection(Paths.PlayerInfo.toString()).insertOne(new Document(doc));

        return playerInfo;
    }

    public PlayerInfo getPlayerInfo(String uuid) {
        Document doc = db.getCollection(Paths.PlayerInfo.toString()).find(Filters.eq("uuid", uuid)).first();
        if (doc == null) {
            return null;
        }

        return new Gson().fromJson(doc.toJson(), PlayerInfo.class);
    }

    public PlayerInfo getPlayerInfoByName(String name) {
        Document doc = db.getCollection(Paths.PlayerInfo.toString()).find(Filters.eq("playername", name)).first();
        if (doc == null) {
            return null;
        }

        return new Gson().fromJson(doc.toJson(), PlayerInfo.class);
    }

    public PlayerSkywarsKits getPlayerSkywarsKits(String uuid) {
        Document doc = db.getCollection(Paths.PlayerSkywarsKits.toString()).find( eq("uuid", uuid)).first();

        return new PlayerSkywarsKits(new Gson().fromJson(doc.toJson(), Map.class));
    }
}
