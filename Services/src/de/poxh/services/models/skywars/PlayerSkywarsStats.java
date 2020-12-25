package de.poxh.services.models.skywars;

import com.google.gson.Gson;

import java.util.Map;

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

    public PlayerSkywarsStats addTogether(PlayerSkywarsStats s) {
        Map<String, Object> data = new Gson().fromJson(new Gson().toJson(s), Map.class);
        Map<String, Object> me = new Gson().fromJson(new Gson().toJson(this), Map.class);

        data.forEach((k, v) -> {
            if (v.getClass() == Integer.class) {
                data.put(k, (Integer)v + (Integer) me.get(k));
            }
        });

        return new Gson().fromJson(new Gson().toJson(data), PlayerSkywarsStats.class);
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
