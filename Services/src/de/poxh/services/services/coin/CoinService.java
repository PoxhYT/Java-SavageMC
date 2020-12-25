package de.poxh.services.services.coin;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import de.poxh.services.helper.Convert;
import de.poxh.services.main.MainService;
import de.poxh.services.models.common.Paths;
import javafx.util.Pair;
import org.bson.Document;

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