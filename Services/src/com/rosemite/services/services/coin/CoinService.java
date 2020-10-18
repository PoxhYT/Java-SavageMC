package com.rosemite.services.services.coin;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.services.helper.Converters;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.models.common.Path;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.models.player.PlayerInfo;
import org.bson.Document;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class CoinService {
    private MongoDatabase db;
    private MainService mainService;

    public CoinService(MongoDatabase db, MainService mainService) {
        this.db = db;
        this.mainService = mainService;
    }

    public int getCoinAmount(String uuid) {
        Document doc = db.getCollection(Paths.PlayerInfo.toString()).find(eq("uuid", uuid)).first();

        return Converters.c(doc.get("coins"));
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

    public Pair<Integer, Boolean> removeCoins(String uuid, int amount) {
        int current = getCoinAmount(uuid);
        int total = current - amount;

        if (total < 0) {
            return new Pair<>(current, false);
        }

        db.getCollection(Paths.PlayerInfo.toString()).updateOne(
                Filters.eq("uuid", uuid),
                combine(set( "coins", total))
        );

        return new Pair<>(total, true);
    }
}
