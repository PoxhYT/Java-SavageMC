package de.lobby.mysql;

import de.lobby.main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamLobbyAutoConnect {
    public static void create(String uuid, String name, int value) {
        if (!isExists(uuid))
            try {
                PreparedStatement ps = Main.connection.getConnection().prepareStatement("INSERT INTO TeamLobbyAutoConnect (UUID, Name, TeamLobbyAutoConnect) VALUES (?, ?, ?)");
                ps.setString(1, uuid.replace("-", ""));
                ps.setString(2, name);
                ps.setInt(3, value);
                ps.execute();
                ps.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

    public static boolean isExists(String uuid) {
        try {
            PreparedStatement ps = Main.connection.getConnection().prepareStatement("SELECT UUID FROM TeamLobbyAutoConnect WHERE UUID=?");
            ps.setString(1, uuid.replace("-", ""));
            ResultSet rs = ps.executeQuery();
            boolean fotze = rs.next();
            return fotze;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public static void setSetting(String uuid, int value) {
        try {
            PreparedStatement ps = Main.connection.getConnection().prepareStatement("UPDATE TeamLobbyAutoConnect SET TeamLobbyAutoConnect = ? WHERE UUID = ?");
            ps.setInt(1, value);
            ps.setString(2, uuid.replace("-", ""));
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Integer getSetting(String uuid) {
        if (isExists(uuid)) {
            ResultSet rs = null;
            int value = 0;
            try {
                PreparedStatement ps = Main.connection.getConnection().prepareStatement("SELECT TeamLobbyAutoConnect FROM TeamLobbyAutoConnect WHERE UUID=?");
                ps.setString(1, uuid.replace("-", ""));
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs != null)
                        value = rs.getInt("TeamLobbyAutoConnect");
                }
                rs = ps.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Integer.valueOf(value);
        }
        return Integer.valueOf(0);
    }
}

