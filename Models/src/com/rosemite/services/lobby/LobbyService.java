package com.rosemite.services.lobby;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.models.service.common.IService;
import com.rosemite.models.service.common.Paths;
import org.bson.Document;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class LobbyService {
    private MongoDatabase db;

    public LobbyService(MongoDatabase db, IService mainService) {
        this.db = db;
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
