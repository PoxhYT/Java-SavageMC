package de.mlgrush.handler;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamHandler {
    private List<Player> teamOne = new ArrayList<>();

    private List<Player> teamTwo = new ArrayList<>();

    private List<Player> spectator = new ArrayList<>();

    private List<Player> noTeam = new ArrayList<>();

    public List<Player> playing = new ArrayList<>();

    private int teamOneSize = 0;

    private int teamTwoSize = 0;

    private int spectatorSize = 0;

    private int noTeamSize = 0;

    public void setSpectator(Player player, boolean value) {
        if (value) {
            this.spectatorSize++;
            this.spectator.add(player);
        } else {
            this.spectatorSize--;
            this.spectator.remove(player);
        }
    }

    public boolean isSpectator(Player player) {
        return this.spectator.contains(player);
    }
}
