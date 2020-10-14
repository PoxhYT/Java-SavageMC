package com.rosemite.services.models;

public class SoupScoreModel {
    public final int NOOB;
    public final int SLOW;
    public final int NORMAL;
    public final int HARD;
    public final int LEGEND;
    public final String UUID;
    public final String PLAYERNAME;

    public SoupScoreModel(int NOOB, int SLOW, int NORMAL, int HARD, int LEGEND, String UUID, String PLAYERNAME) {
        this.NOOB = NOOB;
        this.SLOW = SLOW;
        this.NORMAL = NORMAL;
        this.HARD = HARD;
        this.LEGEND = LEGEND;
        this.UUID = UUID;
        this.PLAYERNAME = PLAYERNAME;
    }
}
