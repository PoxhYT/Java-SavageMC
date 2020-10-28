package de.sw.manager;

import org.bukkit.entity.Player;

import java.util.List;

public class Team {
    private List<Player> players;
    private String teamName;

    public Team(List<Player> players, String teamName) {
        this.players = players;
        this.teamName = teamName;
    }
}
