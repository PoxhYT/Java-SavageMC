package de.lobby.mysql;

import de.lobby.main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SilentLobbyAutoConnect {
    public static void create(String uuid, String name, int value) {
        if (!isExists(uuid))
            try {
                PreparedStatement ps = Main.connection.getConnection().prepareStatement("INSERT INTO SilentLobbyAutoConnect (UUID, Name, SilentLobbyAutoConnect) VALUES (?, ?, ?)");
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
            PreparedStatement ps = Main.connection.getConnection().prepareStatement("SELECT UUID FROM SilentLobbyAutoConnect WHERE UUID=?");
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
            PreparedStatement ps = Main.connection.getConnection().prepareStatement("UPDATE SilentLobbyAutoConnect SET SilentLobbyAutoConnect = ? WHERE UUID = ?");
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
                PreparedStatement ps = Main.connection.getConnection().prepareStatement("SELECT SilentLobbyAutoConnect FROM SilentLobbyAutoConnect WHERE UUID=?");
                ps.setString(1, uuid.replace("-", ""));
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs != null)
                        value = rs.getInt("SilentLobbyAutoConnect");
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
