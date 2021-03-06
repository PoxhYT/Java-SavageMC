package com.rosemite.services.player;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.models.common.Entry;
import com.rosemite.models.service.common.IService;
import com.rosemite.helper.Convert;
import com.rosemite.models.service.common.Paths;
import com.rosemite.models.service.player.PlayerInfo;
import com.rosemite.models.service.skywars.PlayerSkywarsKits;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class PlayerService {
    private MongoDatabase db;

    public PlayerService(MongoDatabase db, IService mainService) {
        this.db = db;
    }

    public PlayerInfo createNewPlayer(String uuid, String displayName) {
        PlayerInfo playerInfo;
        playerInfo = new PlayerInfo(
            false,
            false,
            displayName,
            "Standard",
            uuid,
            500,
            5,
            0,
            "none",
            "NOW",
            Convert.date(),
            true,
            "Lobby-1",
            true,
            true
        );

        String json = new Gson().toJson(playerInfo, PlayerInfo.class);

        Map<String, Object> doc = new Gson().fromJson(json, Map.class);
        db.getCollection(Paths.PlayerInfo.toString()).insertOne(new Document(doc));

        return playerInfo;
    }

    public List<PlayerInfo> mapUUIDsToPlayerInfo(List<String> uuids) {
        List<PlayerInfo> playerInfos = new ArrayList<>();
        for (int i = 0; i < uuids.size(); i++) {
            playerInfos.add(getPlayerInfo(uuids.get(i)));
        }
        return playerInfos;
    }

    public String getPlayerServer(String uuid) {
        Document doc = db.getCollection(Paths.PlayerInfo.toString()).find(eq("uuid", uuid)).first();
        return (String) doc.get("serverName");
    }

    public void setPlayerServer(String uuid, String serverName) {
        db.getCollection(Paths.PlayerInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("serverName", serverName)));
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
        FindIterable<Document> doc1 = db.getCollection(Paths.PlayerSkywarsKits.toString()).find( eq("uuid", uuid));
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

    public void onJoin(String uuid) {
        db.getCollection(Paths.PlayerInfo.val).updateOne((Filters.eq("uuid", uuid)),
                combine(set("isOnline", true))
        );
    }

    public void onLeave(String uuid) {
        db.getCollection(Paths.PlayerInfo.val).updateOne((Filters.eq("uuid", uuid)),
            combine(set("lastSeen", Convert.date()), set("isOnline", false))
        );
    }


    public void banPlayer(String uuid, boolean baned) {
        db.getCollection(Paths.PlayerInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("isBanned", baned)));
    }

    public void setAllowSendFriendRequest(String uuid, boolean allowed) {
        db.getCollection(Paths.PlayerInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("allowSendFriendRequests", allowed)));
    }

    public void setAllowSendPartyRequest(String uuid, boolean allowed) {
        db.getCollection(Paths.PlayerInfo.toString()).updateOne((Filters.eq("uuid", uuid)),
                combine(set("allowSendPartyRequests", allowed)));
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

    public Map.Entry<Integer, Boolean> removeBans(String uuid, int amount) {
        int current = getBanAmount(uuid);
        int total = current - amount;

        if (total < 0) {
            return new Entry<>(current, false);
        }

        db.getCollection(Paths.PlayerInfo.toString()).updateOne(
                Filters.eq("uuid", uuid),
                combine(set( "bans", total))
        );

        return new Entry<>(total, true);
    }
}
