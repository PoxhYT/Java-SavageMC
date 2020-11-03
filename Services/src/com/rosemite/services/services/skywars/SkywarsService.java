package com.rosemite.services.services.skywars;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.models.common.Severity;
import com.rosemite.services.models.skywars.PlayerSkywarsKits;
import de.sw.manager.KitManager;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.*;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class SkywarsService {
    private final MongoDatabase db;
    private final MainService service;
    private List<KitManager> allKits;

    public SkywarsService(MongoDatabase db, MainService mainService) {
        this.service = mainService;
        this.db = db;
        allKits = new ArrayList<>();
    }

    public InsertOneResult initializeKits(String uuid) {
        Map<String, Object> data = new HashMap<>();
        data.put("Standard", true);
        data.put("uuid", uuid);

        return db.getCollection(Paths.PlayerSkywarsKits.toString()).insertOne(new Document(data));
    }

    public UpdateResult updateLatestSelectedKit(String uuid, String kitName) {
        UpdateResult result = db.getCollection(Paths.PlayerInfo.toString()).updateOne(
                Filters.eq("uuid", uuid),
                combine(set("latestSelectedKit", kitName))
        );

        return result;
    }

    public String getLatestSelectedKit(String uuid) {
        return service.getPlayerService().getPlayerInfo(uuid).getLatestSelectedKit();
    }

    public UpdateResult buyKit(String uuid, String kitName) {
        UpdateResult result = db.getCollection(Paths.PlayerSkywarsKits.toString()).updateOne(
                Filters.eq("uuid", uuid),
                combine(set(kitName, true))
        );

        return result;
    }

    public List<KitManager> verifyKits(List<KitManager> kits, String uuid) {
        PlayerSkywarsKits playerKits = service.getPlayerService().getPlayerSkywarsKits(uuid);

        if (playerKits == null) {
            service.getReportService().report(
                    Severity.High,
                    "Player Kits was null but it should not be. Check if this UUID is under players collection: " + uuid,
                    "SkywarsService"
            );
            return kits;
        }

        for (KitManager kit : kits) {
            if (playerKits.hasSkywarsKit(kit.getKitNameLiteralString())) {
                kit.setHasKit(true);
            }
        }

        return kits;
    }

    private void updatePlayerStats() {

    }

    public void getPlayerStats() {

    }

    public void addPlayerStats() {
        getPlayerStats();
    }

    public List<KitManager> getDefaultKits() {
        FindIterable docs = db.getCollection(Paths.DefaultSkywarsKits.toString()).find();

        String json = new Gson().toJson(docs.into(new ArrayList()));

        Type listType = new TypeToken<ArrayList<KitManager>>(){}.getType();
        List<KitManager> list = new Gson().fromJson(json, listType);

        list.sort(Comparator.comparingInt(KitManager::getKitPrice));
        return list;
    }
}
