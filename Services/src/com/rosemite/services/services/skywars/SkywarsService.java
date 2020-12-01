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
import com.rosemite.services.models.skywars.PlayerSkywarsStats;
import de.sw.listener.KitItem;
import de.sw.manager.ItemDocument;
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

    public void updateLatestSelectedKit(String uuid, String kitName) {
        UpdateResult result = db.getCollection(Paths.PlayerInfo.toString()).updateOne(
                Filters.eq("uuid", uuid),
                combine(set("latestSelectedKit", kitName))
        );
    }

    public String getLatestSelectedKit(String uuid) {
        return service.getPlayerService().getPlayerInfo(uuid).getLatestSelectedKit();
    }

    public void buyKit(String uuid, String kitName) {
        UpdateResult result = db.getCollection(Paths.PlayerSkywarsKits.toString()).updateOne(
                Filters.eq("uuid", uuid),
                combine(set(kitName, true))
        );
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

    public PlayerSkywarsStats getPlayerStats(String uuid) {
        Document doc = db.getCollection(Paths.PlayerSkywarsStats.toString()).find(Filters.eq("uuid", uuid)).first();
        if (doc == null) {
            return null;
        }

        return new Gson().fromJson(doc.toJson(), PlayerSkywarsStats.class);
    }

    public void addPlayerStats(String uuid, PlayerSkywarsStats stats) {
        PlayerSkywarsStats res = getPlayerStats(uuid);
        Map<String, Object> data = new Gson().fromJson(new Gson().toJson(stats), Map.class);

        if (res == null) {
            db.getCollection(Paths.PlayerSkywarsStats.toString()).insertOne(new Document(data));
            return;
        }

        data = new Gson().fromJson(new Gson().toJson(res.addTogether(stats)), Map.class);

        db.getCollection(Paths.PlayerSkywarsStats.toString()).insertOne(new Document(data));
    }

    public List<KitManager> getDefaultKits() {
        FindIterable docs = db.getCollection(Paths.DefaultSkywarsKits.toString()).find();

        String json = new Gson().toJson(docs.into(new ArrayList()));

        Type listType = new TypeToken<ArrayList<KitManager>>(){}.getType();
        List<KitManager> list = new Gson().fromJson(json, listType);

        // Assign ...
        for (int i = 0; i < list.size(); i++) {
            List<ItemDocument> items = list.get(i).getItems();

            for (int j = 0; j < items.size(); j++) {
                KitItem kitItem = new KitItem("Some Cool Item", items.get(j).KitEnchantments, items.get(j).ItemId, items.get(j).Amount);
                list.get(i).addKitItem(kitItem);
            }
        }

        list.sort(Comparator.comparingInt(KitManager::getKitPrice));
        return list;
    }
}
