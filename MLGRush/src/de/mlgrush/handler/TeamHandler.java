package de.mlgrush.handler;

import java.util.ArrayList;
import java.util.List;

import de.mlgrush.enums.TeamType;
import de.mlgrush.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

    public void handleTeams() {
        if (Bukkit.getOnlinePlayers().size() == 2) {
            boolean hasTeam1 = false;
            boolean hasTeam2 = false;
            int index = 1;
            for (Player current : Bukkit.getOnlinePlayers()) {
                if (index == 1) {
                    if (hasTeam(current))
                        hasTeam1 = true;
                    index++;
                    continue;
                }
                if (index == 2 &&
                        hasTeam(current))
                    hasTeam2 = true;
            }
            if (hasTeam1 && hasTeam2) {
                System.out.println("[MLGRUSH] Teams handled.");
            } else {
                for (Player current : this.noTeam) {
                    if (!isTeamFull(TeamType.TEAM_1)) {
                        joinTeam(current, TeamType.TEAM_1);
                        current.sendMessage(Main.prefix + "Du wurdest §eTeam §7#1 zugeteilt.");
                        continue;
                    }
                    joinTeam(current, TeamType.TEAM_2);
                    current.sendMessage(Main.prefix + "Du wurdest §eTeam §7#2 zugeteilt.");
                }
                System.out.println("[MLGRUSH] Teams handled.");
            }
        }
    }

    public void fixTeams() {
        TeamType team1 = TeamType.NONE;
        TeamType team2 = TeamType.NONE;
        int index = 1;
        for (Player current : this.playing) {
            if (index == 1) {
                team1 = getPlayerTeam(current);
                index++;
                continue;
            }
            if (index == 2)
                team2 = getPlayerTeam(current);
        }
        if (team1 == team2) {
            System.out.println("[MLGRUSH] Teams need to be fixed! Handling new...");
            handleTeams();
        }
    }

    public boolean isTeamFull(TeamType teamType) {
        if (teamType == TeamType.TEAM_1)
            return (this.teamOneSize == 1);
        if (teamType == TeamType.TEAM_2)
            return (this.teamTwoSize == 1);
        return false;
    }

    public void joinTeam(Player player, TeamType teamType) {
        if (teamType == TeamType.TEAM_1) {
            this.teamOneSize++;
            this.teamOne.add(player);
        } else if (teamType == TeamType.TEAM_2) {
            this.teamTwoSize++;
            this.teamTwo.add(player);
        } else if (teamType == TeamType.NONE) {
            this.noTeamSize++;
            this.noTeam.add(player);
        }
    }

    public void leaveTeam(Player player, TeamType teamType) {
        if (teamType == TeamType.TEAM_1) {
            this.teamOneSize--;
            this.teamOne.remove(player);
        } else if (teamType == TeamType.TEAM_2) {
            this.teamTwoSize--;
            this.teamTwo.remove(player);
        } else if (teamType == TeamType.NONE) {
            this.noTeamSize--;
            this.noTeam.remove(player);
        }
    }

    public boolean hasTeam(Player player) {
        return (this.teamOne.contains(player) || this.teamTwo.contains(player));
    }

    public boolean hasTeamNone(Player player) {
        return this.noTeam.contains(player);
    }

    public boolean isInTeam(TeamType teamType, Player player) {
        return (getPlayerTeam(player) == teamType);
    }

    public List<Player> getPlayersInTeam(TeamType teamType) {
        List<Player> output = new ArrayList<>();
        if (teamType == TeamType.TEAM_1) {
            output.addAll(this.teamOne);
        } else {
            output.addAll(this.teamTwo);
        }
        return output;
    }

    public TeamType getPlayerTeam(Player player) {
        if (this.teamOne.contains(player))
            return TeamType.TEAM_1;
        if (this.teamTwo.contains(player))
            return TeamType.TEAM_2;
        return TeamType.NONE;
    }
}

