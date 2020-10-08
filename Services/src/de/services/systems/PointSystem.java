package de.services.systems;

import de.services.helper.Log;
import de.services.mysql.MySQL;
import de.soup.storage.SpeedType;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import static org.apache.commons.lang.StringUtils.repeat;

public class PointSystem {
    private MySQL mySQL;

    public PointSystem(MySQL sql) {
        this.mySQL = sql;
    }

    public void saveForSoupScore(Player player, SpeedType type, String time, int droppedSoups) {
//        String sql = "INSERT INTO soup_scores (uuid, soup_noob, soup_noob_time ) VALUES ( ?, ?, ? )";
//        String sql = getSoupScoreString();

        String sql = "UPDATE soup_scores SET (soup_noob) WHERE 20 > soup_noob  VALUES ( ? )";
        Log.log(type.toString());



//        Log.log(sql);
//
//        Log.log(player.getUniqueId());
//        Log.log(type);
//        Log.log(time);
//        Log.log(droppedSoups);

        try {
            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, 300);

            preparedStatement.executeUpdate();
            Log.log("1");
        } catch (SQLException e)  {
            e.getStackTrace();
//            e.getStackTrace().printStackTrace(System.out);
//            e.printStackTrace(pw);
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter();
////            System.out.println(.toString());
            System.out.println(e);
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace()[0]);
            System.out.println(e.getStackTrace()[0].toString());
        }
//        try
//            PreparedStatement preparedStatement = mySQL.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, player.getUniqueId().toString());
//            preparedStatement.setInt(2, droppedSoups);
//            preparedStatement.setString(3, "00:00:10");
//
//            preparedStatement.executeUpdate();
//            Log.log("1");
//        } catch (SQLException e)  {
//            e.getStackTrace();
////            e.getStackTrace().printStackTrace(System.out);
////            e.printStackTrace(pw);
////            StringWriter sw = new StringWriter();
////            PrintWriter pw = new PrintWriter();
//////            System.out.println(.toString());
//            System.out.println(e);
//            System.out.println(e.getErrorCode());
//            System.out.println(e.getMessage());
//            System.out.println(e.getStackTrace()[0]);
//            System.out.println(e.getStackTrace()[0].toString());
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

    private String getSoupScoreString() {
        final String sn = "soup_noob";
        final String ss = "soup_slow";
        final String snl = "soup_normal";
        final String sh = "soup_hard";
        final String sl = "soup_legend";

        final  String t = "_time";

        String s = repeat("?, ", 11);

        s = s.substring(0, s.length()-2);

        Log.log(s);

        return "INSERT INTO soup_scores (uuid, "+sn+", "+ss+", "+snl+", "+sh+", "+sl+", "+sn+t+", "+ss+t+", "+snl+t+", "+sh+t+", "+sl+t+" ) VALUES " +
                "("+s+")";
    }
}
