package com.rosemite.models.service.soup;


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

    public SoupScoreModel(String uuid, String displayName) {
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

        this.UUID = uuid;
        this.PLAYERNAME = displayName;
    }
}
