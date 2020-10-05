package de.services.pointsystem;

import de.services.mysql.MySQL;
import de.soup.commands.SoupCommand;
import de.soup.events.SoupListener;
import de.soup.storage.SpeedType;
import org.bukkit.entity.Player;

public class PointSystem {
    private MySQL sql;

    public PointSystem(MySQL sql) {
        this.sql = sql;
    }

    public void saveForSoupScore(Player player, SpeedType type, int score) {
        // TODO: Receive Score On gameOver or /soup stop command
        // TODO: Save for Player

    }

    public void getScoresForSoup(Player player) {
        // TODO: Search by player
    }

    public void getScoresForSoup(SpeedType type) {
        // TODO: Search by type
    }
}
