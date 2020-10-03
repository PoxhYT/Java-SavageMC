package de.sw.manager;

import org.bukkit.entity.Player;

import java.util.List;

public class TeamManager {

    private List<Team> teams;
    private String teamName;
    private int playersInTeam;
    private List<Player> players;

    public TeamManager(List<Team> teams, String teamName, int playersInTeam, List<Player> players) {
        this.teams = teams;
        this.teamName = teamName;
        this.playersInTeam = playersInTeam;
        this.players = players;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

}
