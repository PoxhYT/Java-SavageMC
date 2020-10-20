package com.rosemite.services.models.soup;

import org.bukkit.entity.Player;

public class SoupScoreModel {
    public final int NOOB;
    public final int SLOW;
    public final int NORMAL;
    public final int HARD;
    public final int LEGEND;

    public final String NOOBTIME;
    public final String SLOWTIME;
    public final String NORMALTIME;
    public final String HARDTIME;
    public final String LEGENDTIME;

    public final String UUID;
    public final String PLAYERNAME;

    public SoupScoreModel(Player player) {
        int ds = 0;
        String dst = "00:00:00";

        this.NOOB = ds;
        this.SLOW = ds;
        this.NORMAL = ds;
        this.HARD = ds;
        this.LEGEND = ds;

        this.NOOBTIME = dst;
        this.SLOWTIME = dst;
        this.NORMALTIME = dst;
        this.HARDTIME = dst;
        this.LEGENDTIME = dst;

        this.UUID = player.getUniqueId().toString();
        this.PLAYERNAME = player.getDisplayName();
    }

    public SoupScoreModel(String playerUUID, String playername) {
        int ds = 0;
        String dst = "00:00:00:00";

        this.NOOB = ds;
        this.SLOW = ds;
        this.NORMAL = ds;
        this.HARD = ds;
        this.LEGEND = ds;

        this.NOOBTIME = dst;
        this.SLOWTIME = dst;
        this.NORMALTIME = dst;
        this.HARDTIME = dst;
        this.LEGENDTIME = dst;

        this.UUID = playerUUID;
        this.PLAYERNAME = playername;
    }
}
