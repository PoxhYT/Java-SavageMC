package de.lobby.mysql;

import de.lobby.main.Main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestQueue implements Runnable {
    private List<String> requests = new ArrayList<>();

    private boolean running = true;

    private Thread thread = new Thread(this);

    public void setRunning(boolean running) {
        this.running = running;
        if (running)
            this.thread.start();
    }

    public void addToQueue(String qry) {
        this.requests.add(qry);
    }

    public void run() {
        while (this.running) {
            if (this.requests.size() > 0)
                for (int i = 0; i < this.requests.size(); i++) {
                    try {
                        String qry = this.requests.get(i);
                        PreparedStatement stmt = Main.connection.getConnection().prepareStatement(qry);
                        stmt.executeUpdate();
                        this.requests.remove(i);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            try {
                Thread.sleep(25L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

