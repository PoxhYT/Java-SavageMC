package org.rosemite.services.systems;

import org.bukkit.entity.Player;

import java.util.UUID;

import static org.apache.commons.lang.StringUtils.repeat;

public class PointSystem {
//    public void saveForSoupScore(Player player, SpeedType type, String time, int droppedSoups) {
    public void saveForSoupScore(Player player, Object type, String time, int droppedSoups) {
        // TODO: Receive Score On gameOver or /soup stop command
        // TODO: Save for Player
    }

    public void getScoresForSoup(UUID id)  {
        // TODO: Search by UUID
    }

    public void getScoresForSoup(Object type) {
        // TODO: Search by type
    }
}
