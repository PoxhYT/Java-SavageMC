package com.rosemite.services.systems;

import com.rosemite.services.backend.http.Http;
import com.rosemite.services.backend.http.HttpType;
import com.rosemite.services.helper.Log;
import com.rosemite.services.models.HttpResponse;
import com.rosemite.services.models.Path;
import com.rosemite.services.models.Paths;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PointSystem {
    private Http http;

    public PointSystem(Http http) {
        this.http = http;
    }

//    public void saveForSoupScore(Player player, SpeedType type, String time, int droppedSoups) {
    public void saveForSoupScore(Player player, Object type, String time, int droppedSoups) {
        Log.d(player.getUniqueId());
        Log.d(type);
        Log.d(time);
        Log.d(droppedSoups);

        Map<String, Object> body = new HashMap<>();
        body.put("UUID", player.getUniqueId().toString());
        body.put(type.toString(), droppedSoups);

        Path path = new Path(Paths.SoupTraining, player.getUniqueId().toString());

        Log.d(path.get());

        // TODO: Only save score if current score is higher
        try {
            HttpResponse res =  http.request(HttpType.POST,body, new Path(Paths.SoupTraining, player.getUniqueId().toString()));

            Log.d(res.statusCode);
            Log.d(res.content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getScoresForSoup(UUID id)  {
        // TODO: Search by UUID
    }

    public void getScoresForSoup(Object type) {
        // TODO: Search by type
    }
}
