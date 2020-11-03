package com.rosemite.services.models.skywars;

public class PlayerSkywarsStats {
    private int wins;
    private int kills;
    private int deaths;
    private final String playername;
    private final String uuid;
    private int points;
    private int playedGames;

    public PlayerSkywarsStats(String playername, String uuid) {
        this.playername = playername;
        this.uuid = uuid;
    }

    public int getWins() {
        return wins;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public String getPlayername() {
        return playername;
    }

    public String getUuid() {
        return uuid;
    }

    public int getPoints() {
        return points;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void addKills(int amount) {
        kills += amount;
    }

    public void addWins(int amount) {
        wins += amount;
    }

    public void addDeaths(int amount) {
        deaths += amount;
    }

    public void addPoints(int amount) {
        points += amount;
    }

    public void addPlayedGames(int amount) {
        playedGames += amount;
    }
}
