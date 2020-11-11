package de.cba.manager;

import org.bukkit.Location;

public class TeleportUser {

    private String name;
    private TeleportType teleportType;
    private Location hereLoc;

    public TeleportUser(String name, TeleportType teleportType, Location hereLoc) {
        this.name = name;
        this.teleportType = teleportType;
        this.hereLoc = hereLoc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeleportType getTeleportType() {
        return teleportType;
    }

    public void setTeleportType(TeleportType teleportType) {
        this.teleportType = teleportType;
    }

    public Location getHereLoc() {
        return hereLoc;
    }

    public void setHereLoc(Location hereLoc) {
        this.hereLoc = hereLoc;
    }
}
