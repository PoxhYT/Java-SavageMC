package de.lobby.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.lobby.main.Main;
import org.bukkit.Bukkit;

public class MySQL {
    public static String host = "localhost";

    public static String port = "3306";

    public static String database = "lobby";

    public static String username = "root";

    public static String password = "bruh1234";

    public static Connection con;

    public static boolean isConnected() {
        return (con != null);
    }

    public static void connect() {
        if (!isConnected())
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
                Bukkit.getConsoleSender().sendMessage(Main.prefix + "§r§7Die §r§eVerbindung §r§7mit der §r§5MySQL Datenbank §r§7wurde hergestellt.");
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(Main.prefix + "§r§7Es konnte keine §r§eVerbindung §r§7mit der §r§5MySQL Datenbank §r§7hergestellt werden.");
            }
    }

    public static void disconnect() {
        try {
            con.close();
            Bukkit.getConsoleSender().sendMessage(Main.prefix + "§r§7Die §r§eVerbindung zur §r§5MySQL §r§7konnte §r§aerfolgreich §r§cgeschlossen §r§7werden.");
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(Main.prefix + "§r§7Die §r§eVerbindung zur §r§5MySQL §r§7konnte nicht §r§cgeschlossen §r§7werden.");
        }
    }

    public static PreparedStatement getStatement(String sql) {
        if (isConnected())
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                return ps;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }

    public static void update(String qry) {
        if (isConnected())
            try {
                con.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static ResultSet getResult(String sql) {
        if (isConnected())
            try {
                PreparedStatement ps = getStatement(sql);
                ResultSet rs = ps.executeQuery();
                return rs;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }
}
