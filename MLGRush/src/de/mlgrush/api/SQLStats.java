package de.mlgrush.api;

import de.mlgrush.main.Main;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLStats {

    public static boolean inList(Player player) {
        String uuid = player.getUniqueId().toString();
        ResultSet resultSet = Main.mySQL.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
        try {
            return  resultSet.next();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean playerExists(String uuid) {

        try {
            ResultSet resultSet = Main.mySQL.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");

            if (resultSet.next()) {
                return resultSet.getString("UUID") != null;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createPlayer(String uuid) {

        if (!(playerExists(uuid))) {
            Main.mySQL.update("INSERT INTO Stats(UUID, KILLS, DEATHS, WINS) VALUES ('" + uuid + "', '0', '0', '0');");
        }
    }

    public static Integer getKills(String uuid) {
        Integer i = 0;

        if (playerExists(uuid)) {
            try {
                ResultSet resultSet = Main.mySQL.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
                if((!resultSet.next()) || (Integer.valueOf(resultSet.getInt("KILLS")) == null));
                i = resultSet.getInt("KILLS");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            createPlayer(uuid);
            getKills(uuid);

        }
        return i;
    }

    public static Integer getDeaths(String uuid) {
        Integer i = 0;

        if (playerExists(uuid)) {
            try {
                ResultSet resultSet = Main.mySQL.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
                if((!resultSet.next()) || (Integer.valueOf(resultSet.getInt("DEATHS")) == null));
                i = resultSet.getInt("DEATHS");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            createPlayer(uuid);
            getDeaths(uuid);

        }
        return i;
    }

    public static Integer getWins(String uuid) {
        Integer i = 0;

        if (playerExists(uuid)) {
            try {
                ResultSet resultSet = Main.mySQL.query("SELECT * FROM Stats WHERE UUID= '" + uuid + "'");
                if((!resultSet.next()) || (Integer.valueOf(resultSet.getInt("WINS")) == null));
                i = resultSet.getInt("WINS");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            createPlayer(uuid);
            getWins(uuid);
        }
        return i;
    }

    public static void setKills(String uuid, Integer kills) {
        if (playerExists(uuid)) {
            Main.mySQL.update("UPDATE Stats SET KILLS= '" + kills + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            setKills(uuid, kills);
        }
    }

    public static void setDeaths(String uuid, Integer deaths) {
        if (playerExists(uuid)) {
            Main.mySQL.update("UPDATE Stats SET DEATHS= '" + deaths + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            setDeaths(uuid, deaths);
        }
    }

    public static void setWins(String uuid, Integer wins) {
        if (playerExists(uuid)) {
            Main.mySQL.update("UPDATE Stats SET WINS= '" + wins + "' WHERE UUID= '" + uuid + "';");
        } else {
            createPlayer(uuid);
            setWins(uuid, wins);
        }
    }

    public static void addKills(String uuid, Integer kills) {
        if (playerExists(uuid)) {
            setKills(uuid, Integer.valueOf(getKills(uuid).intValue() + kills.intValue()));
        } else {
            createPlayer(uuid);
            addKills(uuid, kills);
        }
    }

    public static void addDeaths(String uuid, Integer deaths) {
        if (playerExists(uuid)) {
            setDeaths(uuid, Integer.valueOf(getDeaths(uuid).intValue() + deaths.intValue()));
        } else {
            createPlayer(uuid);
            addDeaths(uuid, deaths);
        }
    }

    public static void addWins(String uuid, Integer wins) {
        if (playerExists(uuid)) {
            setWins(uuid, Integer.valueOf(getWins(uuid).intValue() + wins.intValue()));
        } else {
            createPlayer(uuid);
            addWins(uuid, wins);
        }
    }

    public static void removeKills(String uuid, Integer kills) {
        if (playerExists(uuid)) {
            setKills(uuid, Integer.valueOf(getKills(uuid).intValue() + kills.intValue()));
        } else {
            createPlayer(uuid);
            removeKills(uuid, kills);
        }
    }

    public static void removeDeaths(String name, String uuid, Integer deaths) {
        if (playerExists(uuid)) {
            setDeaths(uuid, Integer.valueOf(getDeaths(uuid).intValue() + deaths.intValue()));
        } else {
            createPlayer(uuid);
            removeDeaths(name, uuid, deaths);
        }
    }

    public static void removeWins(String uuid, Integer wins) {
        if (playerExists(uuid)) {
            setWins(uuid, Integer.valueOf(getWins( uuid).intValue() + wins.intValue()));
        } else {
            createPlayer(uuid);
            removeWins(uuid, wins);
        }
    }
}
