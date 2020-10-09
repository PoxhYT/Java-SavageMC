package com.rosemite.services.systems;

import com.rosemite.services.helper.Log;
import com.rosemite.services.models.SpeedType;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.apache.commons.lang.StringUtils.repeat;

public class PointSystem {
//    public void saveForSoupScore(Player player, SpeedType type, String time, int droppedSoups) {
    public void saveForSoupScore(Player player, Object type, String time, int droppedSoups) {
        Log.log(player.getUniqueId());
        Log.log(type);
        Log.log(time);
        Log.log(droppedSoups);
        // TODO: Save for Player
    }

    public void getScoresForSoup(UUID id)  {
        // TODO: Search by UUID
    }

    public void getScoresForSoup(SpeedType type) {
        // TODO: Search by type
    }
}
