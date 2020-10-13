package com.rosemite.services.systems;

import com.rosemite.services.backend.http.Http;
import com.rosemite.services.helper.Log;
import org.bukkit.entity.Player;

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
        // TODO: Save for Player
    }

    public void getScoresForSoup(UUID id)  {
        // TODO: Search by UUID
    }

    public void getScoresForSoup(Object type) {
        // TODO: Search by type
    }
}
