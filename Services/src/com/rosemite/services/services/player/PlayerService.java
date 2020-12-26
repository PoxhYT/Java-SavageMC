package com.rosemite.services.services.player;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.services.helper.Convert;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.models.player.PlayerInfo;
import com.rosemite.services.models.skywars.PlayerSkywarsKits;
import javafx.util.Pair;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.Map;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class PlayerService {
    private MongoDatabase db;

    public PlayerService(MongoDatabase db, MainService mainService) {
        this.db = db;
        db.listCollections().forEach(document -> {
            Log.d("ALRIGHT!");
        });
    }

    public PlayerInfo createNewPlayer(Player player) {
        PlayerInfo playerInfo = new PlayerInfo(
            false,
            false,
            player.getDisplayName(),
            "Standard",
            player.getUniqueId().toString(),
            500,
            5,
            0,
            "none"
        );

        String json = new Gson().toJson(playerInfo, PlayerInfo.class);

        Map<String, Object> doc = new Gson().fromJson(json, Map.class);
        db.getCollection(Paths.PlayerInfo.toString()).insertOne(new Document(doc));

        return playerInfo;
    }

    public void setReceivedRewards(String uuid, boolean bool) {
        db.getCollection(Paths.PlayerInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("receivedReward", bool)));
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
        Log.d(doc);
        Log.d(uuid);
        FindIterable<Document> doc1 = db.getCollection(Paths.PlayerSkywarsKits.toString()).find( eq("uuid", uuid));
        doc1.forEach(document -> {
            Log.d("HELLO JOW ARE YOU");
                }
        );
        return new PlayerSkywarsKits(new Gson().fromJson(doc.toJson(), Map.class));
    }


    public void setRewardDate(String uuid, String date) {
        db.getCollection(Paths.PlayerInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("receiveReward", date)));
    }

    public String getRewardDate(String uuid) {
        Document doc = db.getCollection(Paths.PlayerInfo.toString()).find(eq("uuid", uuid)).first();
        return (String) doc.get("receiveReward");
    }

    public void setPlayerBan(String uuid, String date) {
        db.getCollection(Paths.PlayerInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("endBanDate", date)));
    }

    public void banPlayer(String uuid, boolean baned) {
        db.getCollection(Paths.PlayerInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("isBanned", baned)));
    }

    public int getBanAmount(String uuid) {
        Document doc = db.getCollection(Paths.PlayerInfo.toString()).find(eq("uuid", uuid)).first();
        return Convert.c(doc.get("bans"));
    }

    public int addBans(String uuid, int amount) {
        int current = getBanAmount(uuid);
        int total = current + amount;

        db.getCollection(Paths.PlayerInfo.toString()).updateOne(
                Filters.eq("uuid", uuid),
                combine(set("bans", total))
        );
        return total;
    }

    public Pair<Integer, Boolean> removeBans(String uuid, int amount) {
        int current = getBanAmount(uuid);
        int total = current - amount;

        if (total < 0) {
            return new Pair<>(current, false);
        }

        db.getCollection(Paths.PlayerInfo.toString()).updateOne(
                Filters.eq("uuid", uuid),
                combine(set( "bans", total))
        );

        return new Pair<>(total, true);
    }
}
