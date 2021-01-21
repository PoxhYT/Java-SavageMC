package de.poxh.signsystem.manager;

import org.bukkit.block.Sign;

import java.util.ArrayList;
import java.util.List;

public class ServerSign {

    private final Sign sign;
    private String servername;
    private int serverport;
    private int playersize;
    private int maxplayersize;
    private String mapname;
    private String title;
    private boolean serverOnline;

    public ServerSign(Sign sign, String servername, int serverport, int playersize, int maxplayersize, String mapname, String title, boolean serverOnline) {
        this.sign = sign;
        this.servername = servername;
        this.serverport = serverport;
        this.playersize = playersize;
        this.maxplayersize = maxplayersize;
        this.mapname = mapname;
        this.title = title;
        this.serverOnline = serverOnline;
    }

    public ServerSign(String servername, int serverport, int playersize, int maxplayersize, String mapname, String title, boolean serverOnline) {
        this.servername = servername;
        this.serverport = serverport;
        this.playersize = playersize;
        this.maxplayersize = maxplayersize;
        this.mapname = mapname;
        this.title = title;
        this.serverOnline = serverOnline;
    }

    public Sign getSign() {
        return sign;
    }

    public String getServername() {
        return servername;
    }

    public int getServerport() {
        return serverport;
    }

    public int getPlayersize() {
        return playersize;
    }

    public int getMaxplayersize() {
        return maxplayersize;
    }

    public String getMapname() {
        return mapname;
    }

    public String getTitle() {
        return title;
    }

    public boolean isServerOnline() {
        return serverOnline;
    }
}
