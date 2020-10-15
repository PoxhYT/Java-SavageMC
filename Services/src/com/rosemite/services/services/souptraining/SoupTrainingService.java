package com.rosemite.services.services.souptraining;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rosemite.services.services.http.Http;
import com.rosemite.services.models.http.HttpType;
import com.rosemite.services.helper.Log;
import com.rosemite.services.models.http.HttpResponse;
import com.rosemite.services.models.common.Path;
import com.rosemite.services.models.common.Paths;
import com.rosemite.services.models.soup.SoupScoreModel;
import javafx.util.Pair;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class SoupTrainingService {
    private final Http http;

    public SoupTrainingService(Http http) {
        this.http = http;
    }

    public boolean saveScore(Player player, Object type, String time, int droppedSoups) {
        Pair<SoupScoreModel, Map<String, Object>> previousScore = getAllScores(player.getUniqueId());

        if (previousScore == null) {
            return false;
        }

        int score = -1;

        if (previousScore.getValue() != null && previousScore.getValue().containsKey(type.toString())) {
            score = (int) Math.round((double)previousScore.getValue().get(type.toString()));
        }

        if (droppedSoups > score) {
            Map<String, Object> body = new HashMap<>();

            body.put("UUID", player.getUniqueId().toString());
            body.put(type.toString(), droppedSoups);
            body.put("PLAYERNAME", player.getDisplayName());

            Path path = new Path(Paths.SoupTraining, player.getUniqueId().toString());

            try {
                HttpResponse res = http.request(HttpType.POST,body, path);

                if (res.statusCode != 200) {
                    http.reportError(res.content);
                    return false;
                }

                Log.d("New Record!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        return false;
    }

    public Pair<SoupScoreModel, Map<String, Object>> getAllScores(UUID id)  {
        try {
            HttpResponse res =  http.request(HttpType.GET,null, new Path(Paths.SoupTraining, id.toString()));

            if (res.statusCode != 200) {
                http.reportError(res.content);
                return null;
            }

            String json = new Gson().toJson(res.getAsMap().get("data"));

            return new Pair<>(
                    new Gson().fromJson(json, SoupScoreModel.class),
                    new Gson().fromJson(json, Map.class)
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SoupScoreModel> getAllScores() {
        try {
            Map<String, String> header = new HashMap<>();
            header.put("collection", "true");

            HttpResponse res = http.request(HttpType.GET,null, new Path(Paths.SkywarsKits, ""), header);

            if (res.statusCode != 200) {
                http.reportError(res.content);
                return null;
            }

            String json = new Gson().toJson(res.getAsMap().get("data"));

            Type listType = new TypeToken<ArrayList<SoupScoreModel>>(){}.getType();
            List<SoupScoreModel> list = new Gson().fromJson(json, listType);

            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
