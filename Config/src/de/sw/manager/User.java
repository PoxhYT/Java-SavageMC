package de.sw.manager;

import java.text.DecimalFormat;
import java.util.List;

public class User {
    private String uuid;

    private String name;

    private List<String> kits;

    private int kills;

    private int deaths;

    private int wins;

    private int loses;

    private int coins;

    private String currentKit;

    private int team;

    private String prefix;

    public User(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public void setCurrentKit(String currentkit) {
        this.currentKit = currentkit;
    }

    public String getCurrentKit() {
        return currentKit;
    }

    public void addKills(int value) {
        this.kills += value;
    }

    public void addDeaths(int value) {
        this.deaths += value;
    }

    public void addWins(int value) {
        this.wins += value;
    }

    public void addLoses(int value) {
        this.loses += value;
    }

    public void addCoins(int value) {
        this.coins += value;
    }

    public void removeCoins(int value) {
        this.coins -= value;
    }

    public int getCoins() {
        return this.coins;
    }

    public int getKills() {
        return this.kills;
    }

    public int getLoses() {
        return this.loses;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public int getWins() {
        return this.wins;
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return uuid;
    }

    public List<String> getKits() {
        return kits;
    }

    public String getKD() {
        double kills = this.kills;
        double deaths = this.deaths;
        if (kills == 0.0D)
            return String.valueOf(deaths);
        if (deaths == 0.0D)
            return String.valueOf(kills);
        double kd = deaths / kills;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        if (decimalFormat.format(kd).endsWith("0"))
            return String.valueOf(kd);
        return decimalFormat.format(kd);
    }
}
