package com.rosemite.services.services.reward;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.models.player.PlayerInfo;
import com.rosemite.services.models.reward.RewardInfo;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.Map;

public class RewardServices {
    private MongoDatabase db;

    public RewardServices(MongoDatabase db, MainService mainService) {
        this.db = db;
    }

    public RewardInfo createNewPlayer(Player player) {
        RewardInfo rewardInfo = new RewardInfo(
                player.getDisplayName(),
                player.getUniqueId().toString(),
                "JETZT",
                false,
                "JETZT",
                false,
                "JETZT",
                false,
                "JETZT",
                false,
                "JETZT",
                false,
                "JETZT",
                false
        );

        String json = new Gson().toJson(rewardInfo, RewardInfo.class);
        Map<String, Object> doc = new Gson().fromJson(json, Map.class);
        db.getCollection(Paths.RewardInfo.toString()).insertOne(new Document(doc));

        return rewardInfo;
    }

}
