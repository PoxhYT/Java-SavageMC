package com.rosemite.services.services.souptraining;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.services.helper.Convert;
import com.rosemite.services.main.MainService;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.models.player.PlayerInfo;
import com.rosemite.services.models.soup.SoupScoreModel;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class SoupTrainingService {
    private final MongoCollection<Document> collection;
    private final MongoDatabase db;

    private MainService service;

    public SoupTrainingService(MongoDatabase db) {
        this.db = db;
        this.collection = db.getCollection(Paths.SoupTraining.toString());
        service = MainService.getService(null);
    }

    public void saveScore(Player player, Object type, String time, int droppedSoups, int coins) {
        Document doc = collection.find(Filters.eq("UUID", player.getUniqueId().toString())).first();

        if (doc == null) {
            // Todo: Report
            MainService.getService(null).reportError("On Save SoupTraining Document was null");

            // Todo: Create missing Doc
            initializePlayer(player);
        }

        // Add Coins
        Document document = db.getCollection(Paths.PlayerInfo.toString()).find(Filters.eq("uuid", player.getUniqueId().toString())).first();
        coins += Convert.c(document.get("coins"));

        db.getCollection(Paths.PlayerInfo.toString()).updateOne(Filters.eq("uuid", player.getUniqueId().toString()),
            combine(
                    set("coins", coins)
        ));

        // Update Score
        if (doc != null && Convert.c(doc.get(type.toString())) > droppedSoups) {
            return;
        }

        collection.updateOne(Filters.eq("UUID", player.getUniqueId().toString()),
            combine(
                    set(type.toString(), droppedSoups),
                    set(type.toString()+"TIME", time)
        ));
    }

    public SoupScoreModel getPlayerScores(String uuid)  {
        Document doc = collection.find(Filters.eq("uuid", uuid)).first();

        if (doc == null) {
            // Todo: Report
            service.reportError("On Save SoupTraining Document was null");

            // Todo: Create missing Doc
            doc = initializePlayer(service.getPlayerService().getPlayerInfo(uuid));
        }

        String json = new Gson().toJson(doc);

        SoupScoreModel scores = new Gson().fromJson(json, SoupScoreModel.class);

        return scores;
    }

    public List<SoupScoreModel> getEveryScore() {
        List<Document> docs = collection.find().into(new ArrayList<>());

        String json = new Gson().toJson(docs);

        Type listType = new TypeToken<ArrayList<SoupScoreModel>>(){}.getType();
        List<SoupScoreModel> list = new Gson().fromJson(json, listType);

        return list;
    }

    public void initializePlayer(Player player) {
        SoupScoreModel model = new SoupScoreModel(player);

        String json = new Gson().toJson(model);
        Map<String, Object> doc = new Gson().fromJson(json, Map.class);

        collection.insertOne(new Document(doc));
    }

    public Document initializePlayer(PlayerInfo playerInfo) {
        SoupScoreModel model = new SoupScoreModel(playerInfo.getUuid() ,playerInfo.getPlayername());

        String json = new Gson().toJson(model);
        Map<String, Object> doc = new Gson().fromJson(json, Map.class);

        Document newDoc = new Document(doc);

        collection.insertOne(newDoc);
        return newDoc;
    }
}
