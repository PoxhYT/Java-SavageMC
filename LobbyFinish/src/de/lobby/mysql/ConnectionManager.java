package de.lobby.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    public String host = "HOST Direkt / Config";

    public String user = "USER Direkt / Config";

    public String password = "PASSWORD Direkt / Config";

    public String database = "DATABASE Direkt / Config";

    public String port = "PORT Direkt / Config";

    public Connection con;

    private RequestQueue requestQ;

    public ConnectionManager(String host, String user, String password, String database, String port, boolean autoReconnect) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.database = database;
        this.port = port;
        autoReconnect = true;
        this.requestQ = new RequestQueue();
        this.requestQ.setRunning(true);
    }

    public Connection getConnection() {
        return this.con;
    }

    public void connect() {
        if (this.con == null)
            try {
                this.con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void close() {
        if (this.con != null)
            try {
                this.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void update(String qry) {
        this.requestQ.addToQueue(qry);
    }

    public ResultSet query(String qry) {
        ResultSet rs = null;
        try {
            Statement st = this.con.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
        return rs;
    }
}
