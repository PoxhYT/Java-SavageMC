package de.services.pointsystem;

import de.services.helper.Log;
import de.services.mysql.MySQL;
import de.soup.commands.SoupCommand;
import de.soup.events.SoupListener;
import de.soup.storage.SpeedType;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PointSystem {
    private MySQL mySQL;

    public PointSystem(MySQL sql) {
        this.mySQL = sql;
    }

    public void saveForSoupScore(Player player, SpeedType type, String time, int droppedSoups) {
        String sql = "INSERT INTO scores (uuid, droppedSoups) VALUES (?, ?)";

        Log.log(player.getUniqueId());
        Log.log(type);
        Log.log(time);
        Log.log(droppedSoups);

//        try {
//            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, player.getUniqueId().toString());
//            preparedStatement.setInt(2, score);
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException e)  {
//            e.getStackTrace();
//        }
        // TODO: Receive Score On gameOver or /soup stop command
        // TODO: Save for Player

    }

    public void getScoresForSoup(UUID id) throws SQLException {
        // TODO: Search by UUID
        String sql = "SELECT * FROM scores WHERE uuid=" + id.toString();

        PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    public void getScoresForSoup(SpeedType type) {
        // TODO: Search by type
    }
}
