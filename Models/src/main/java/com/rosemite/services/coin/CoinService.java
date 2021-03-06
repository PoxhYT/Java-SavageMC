package com.rosemite.services.coin;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.helper.Log;
import com.rosemite.models.common.Entry;
import com.rosemite.models.service.common.IService;
import com.rosemite.helper.Convert;
import com.rosemite.models.service.common.Paths;
import org.bson.Document;

import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class CoinService {
    private MongoDatabase db;
    private IService mainService;

    public CoinService(MongoDatabase db, IService mainService) {
        this.db = db;
        this.mainService = mainService;
    }

    public int getCoinAmount(String uuid) {
        Document doc = db.getCollection(Paths.PlayerInfo.toString()).find(eq("uuid", uuid)).first();
        return Convert.c(doc.get("coins"));
    }

    public int addCoins(String uuid, int amount) {
        int current = getCoinAmount(uuid);
        int total = current + amount;

        db.getCollection(Paths.PlayerInfo.toString()).updateOne(
            Filters.eq("uuid", uuid),
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

        db.getCollection(Paths.PlayerInfo.toString()).updateOne(
                Filters.eq("uuid", uuid),
                combine(set( "coins", total))
        );

        return new Entry<>(total, true);
    }
}
