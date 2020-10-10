package com.rosemtie.services.systems;

import com.rosemtie.services.helper.Log;
import de.soup.storage.SpeedType;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.apache.commons.lang.StringUtils.repeat;

public class PointSystem {
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

    public void getScoresForSoup(SpeedType type) {
        // TODO: Search by type
    }
}
