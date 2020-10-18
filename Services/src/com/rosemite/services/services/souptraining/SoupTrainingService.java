package com.rosemite.services.services.souptraining;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.rosemite.services.helper.Converters;
import com.rosemite.services.main.MainService;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.models.soup.SoupScoreModel;
import javafx.util.Pair;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.*;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class SoupTrainingService {
    private final MongoDatabase db;
    private final MongoCollection<Document> collection;

    public SoupTrainingService(MongoDatabase db) {
        this.db = db;
        this.collection = db.getCollection(Paths.SoupTraining.toString());
    }

    public void saveScore(Player player, Object type, String time, int droppedSoups) {
        Document doc = collection.find(Filters.eq("UUID", player.getUniqueId().toString())).first();

        if (doc == null) {
            // Todo: Report
            MainService.getService(null).reportError("On Save SoupTraining Document was null");

            // Todo: Create missing Doc
            initializePlayer(player);
        }

        if (doc != null && Converters.c(doc.get(type.toString())) > droppedSoups) {
            return;
        }

        collection.updateOne(Filters.eq("UUID", player.getUniqueId().toString()),
            combine(
                    set(type.toString(), droppedSoups),
                    set(type.toString()+"TIME", time)
        ));
    }

    public Pair<SoupScoreModel, Map<String, Object>> getAllScores(UUID id)  {
        // Todo: Here
//        this.playerCollection.find();
//        return player.getCoins();
        return null;
//        try {
//            HttpResponse res =  http.request(HttpType.GET,null, new Path(Paths.SoupTraining, id.toString()));
//
//            if (res.statusCode != 200) {
//                http.reportError(res.content);
//                return null;
//            }
//
//            String json = new Gson().toJson(res.getAsMap().get("data"));
//
//            return new Pair<>(
//                    new Gson().fromJson(json, SoupScoreModel.class),
//                    new Gson().fromJson(json, Map.class)
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    public List<SoupScoreModel> getAllScores() {
        // Todo: Here
//        this.playerCollection.find();
//        return player.getCoins();
        return null;
//        try {
//            Map<String, String> header = new HashMap<>();
//            header.put("collection", "true");
//
//            HttpResponse res = http.request(HttpType.GET,null, new Path(Paths.SkywarsKits, ""), header);
//
//            if (res.statusCode != 200) {
//                http.reportError(res.content);
//                return null;
//            }
//
//            String json = new Gson().toJson(res.getAsMap().get("data"));
//
//            Type listType = new TypeToken<ArrayList<SoupScoreModel>>(){}.getType();
//            List<SoupScoreModel> list = new Gson().fromJson(json, listType);
//
//            return list;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    public void initializePlayer(Player player) {
        SoupScoreModel model = new SoupScoreModel(player);

        String json = new Gson().toJson(model);
        Map<String, Object> doc = new Gson().fromJson(json, Map.class);

        collection.insertOne(new Document(doc));
    }
}
