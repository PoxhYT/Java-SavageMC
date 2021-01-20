package com.rosemite.services.citybuild;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.helper.Convert;
import com.rosemite.models.citybuild.CityBuildInfos;
import com.rosemite.models.common.Entry;
import com.rosemite.models.service.common.IService;
import com.rosemite.models.service.common.Paths;
import com.rosemite.models.service.player.PlayerInfo;
import org.bson.Document;

import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class CityBuildService {

    private MongoDatabase db;
    private IService mainService;

    public CityBuildService(MongoDatabase db, IService mainService) {
        this.db = db;
        this.mainService = mainService;
    }

    public CityBuildInfos createNewPlayer(String uuid, String playerName, String displayName) {
        CityBuildInfos cityBuildInfos;
        cityBuildInfos = new CityBuildInfos(
                playerName,
                displayName,
                uuid,
                5000
        );

        String json = new Gson().toJson(cityBuildInfos, CityBuildInfos.class);

        Map<String, Object> doc = new Gson().fromJson(json, Map.class);
        db.getCollection(Paths.CityBuild.toString()).insertOne(new Document(doc));

        return cityBuildInfos;
    }

    public CityBuildInfos getPlayerInfo(String uuid) {
        Document doc = db.getCollection(Paths.CityBuild.toString()).find(Filters.eq("UUID", uuid)).first();
        if (doc == null) {
            return null;
        }

        return new Gson().fromJson(doc.toJson(), CityBuildInfos.class);
    }

    public CityBuildInfos getPlayerInfoByName(String name) {
        Document doc = db.getCollection(Paths.CityBuild.toString()).find(Filters.eq("playerName", name)).first();
        if (doc == null) {
            return null;
        }
        return new Gson().fromJson(doc.toJson(), CityBuildInfos.class);
    }

    public int getCoinAmount(String uuid) {
        Document doc = db.getCollection(Paths.CityBuild.toString()).find(eq("UUID", uuid)).first();
        return Convert.c(doc.get("coins"));
    }

    public int addCoins(String uuid, int amount) {
        int current = getCoinAmount(uuid);
        int total = current + amount;

        db.getCollection(Paths.CityBuild.toString()).updateOne(
                Filters.eq("UUID", uuid),
                combine(set("coins", total))
        );

        return total;
    }

    public Map.Entry<Integer, Boolean> removeCoins(String uuid, int amount) {
        int current = getCoinAmount(uuid);
        int total = current - amount;

        if (total < 0) {
            return new Entry<>(current, false);
        }

        db.getCollection(Paths.CityBuild.toString()).updateOne(
                Filters.eq("UUID", uuid),
                combine(set( "coins", total))
        );

        return new Entry<>(total, true);
    }
}
