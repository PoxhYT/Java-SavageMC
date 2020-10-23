package com.rosemite.services.services.player;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.services.helper.Log;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.models.player.PlayerInfo;
import com.rosemite.services.models.player.PlayerSkywarsKits;
import org.bson.Document;
import org.bukkit.entity.Player;

import static com.mongodb.client.model.Filters.eq;

import java.util.Map;

public class PlayerService {
    private MongoDatabase db;

    public PlayerService(MongoDatabase db) {
        this.db = db;
    }

    public PlayerInfo createNewPlayer(Player player) {
        PlayerInfo playerInfo = new PlayerInfo(
            false,
            player.getDisplayName(),
            player.getUniqueId().toString(),
            500
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

    public PlayerSkywarsKits getPlayerSkywarsKits(String uuid) {
        Document doc = db.getCollection(Paths.PlayerSkywarsKits.toString()).find( eq("uuid", uuid)).first();

        return new PlayerSkywarsKits(new Gson().fromJson(doc.toJson(), Map.class));
    }
}