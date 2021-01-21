package de.cb.poxh.manager.teleport;

import org.bukkit.Location;

public class TeleportUser {

    private final String requesterName;

    public TeleportUser(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequesterName() {
        return requesterName;
    }
}
