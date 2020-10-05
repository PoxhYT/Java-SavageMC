package de.services.mysql;

import de.services.main.Main;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL {
    private final String prefix = Main.prefix;
    private Connection connection;
    private String host, database, username, password;
    private int port;

    private final String characterEncoding = "?characterEncoding=latin1&useConfigs=maxPerformance";

    public MySQL(String host, String database, String username, String password, int port)
    {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;

        this.port = port;
    }

    public Connection connect() {
        final String url = "jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database + this.characterEncoding;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("jdbc driver unavailable!");
            return null;
        }

        try {
            connection = DriverManager.getConnection(url, this.username, this.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void disable() {
        try {
            if (connection!=null && !connection.isClosed()){
                connection.close(); //closing the connection field variable.
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
