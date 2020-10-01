package de.sw.mysql;

import de.sw.main.Main;
import de.sw.utils.ServerConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MySQL {

    static File file = new File("plugins/Config", "config.yml");

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    private static MySQL instance = new MySQL();

    private Connection connection;

    public String host;

    public String database;

    public String username;

    public String password;

    public int port;

    public static MySQL getInstance() {
        return instance;
    }

    public void connect() {
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed())
                    return;
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + Main.serverConfig.getHostname() + ":" + Main.serverConfig.getPort() + "/" + Main.serverConfig.getDatabase() + "?autoReconnect=true", Main.serverConfig.getUsername(), Main.serverConfig.getPassword()));
                Bukkit.getConsoleSender().sendMessage(Main.prefix + "§eEine Verbundung zur MySQL-Datenbank wurde hergestellt!");
                createPlayerDataTable();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void createPlayerDataTable() {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS PLAYERDATA(UUID VARCHAR(255) NOT NULL, NAME VARCHAR(255) NOT NULL, IP VARCHAR(255), COINS INT(16), PRIMARY KEY (UUID))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
