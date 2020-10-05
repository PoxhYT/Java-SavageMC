package de.pxav.mlgrush.handler;

// The project Stream - MLGRush is developed and updated by PXAV.
// You are not allowed to claim this as your own, give it to 
// others or even sell it.
//
// Contact me on:
// YouTube: https://www.youtube.com/channel/UCtXSAGTwurKVeEbwEKMlFog
// Twitter: https://twitter.com/OrigPXAV
// 
// (c) 2018 PXAV     

import de.pxav.mlgrush.MLGRush;
import de.pxav.mlgrush.enums.TeamType;
import org.bukkit.Bukkit;
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

    public void setSpectator(final Player player, final boolean value) {
        if (value) {
            spectatorSize++;
            spectator.add(player);
        } else {
            spectatorSize--;
            spectator.remove(player);
        }
    }

    public boolean isSpectator(final Player player) {
        return spectator.contains(player);
    }

    public void handleTeams() {
        if(Bukkit.getOnlinePlayers().size() == 2) {
            boolean hasTeam1 = false;
            boolean hasTeam2 = false;
            int index = 1;
            for (Player current : Bukkit.getOnlinePlayers()) {
                if(index == 1) {
                    if(this.hasTeam(current))
                        hasTeam1 = true;
                    index++;
                } else if(index == 2) {
                    if(this.hasTeam(current))
                        hasTeam2 = true;
                }
            }

            if(hasTeam1 && hasTeam2) {
                System.out.println("[MLGRUSH] Teams handled.");
            } else {
                for (Player current : noTeam) {
                    if (!this.isTeamFull(TeamType.TEAM_1)) {
                        this.joinTeam(current, TeamType.TEAM_1);
                        current.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du wurdest §aTeam #1 §7zugeteilt.");
                    } else {
                        this.joinTeam(current, TeamType.TEAM_2);
                        current.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du wurdest §aTeam #2 §7zugeteilt.");
                    }
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
            if(index == 1) {
                team1 = this.getPlayerTeam(current);
                index++;
            } else if(index == 2) {
                team2 = this.getPlayerTeam(current);
            }
        }

        if(team1 == team2) {
            System.out.println("[MLGRUSH] Teams need to be fixed! Handling new...");
            this.handleTeams();
        }

    }

    public boolean isTeamFull(final TeamType teamType) {
        if (teamType == TeamType.TEAM_1)
            return teamOneSize == 1;
        if (teamType == TeamType.TEAM_2)
            return teamTwoSize == 1;
        return false;
    }

    public void joinTeam(final Player player, final TeamType teamType) {
        if (teamType == TeamType.TEAM_1) {
            teamOneSize++;
            teamOne.add(player);
        } else if (teamType == TeamType.TEAM_2) {
            teamTwoSize++;
            teamTwo.add(player);
        } else if (teamType == TeamType.NONE) {
            noTeamSize++;
            noTeam.add(player);
        }
    }

    public void leaveTeam(final Player player, final TeamType teamType) {
        if(teamType == TeamType.TEAM_1) {
            teamOneSize--;
            teamOne.remove(player);
        } else if (teamType == TeamType.TEAM_2) {
            teamTwoSize--;
            teamTwo.remove(player);
        } else if (teamType == TeamType.NONE) {
            noTeamSize--;
            noTeam.remove(player);
        }
    }

    public boolean hasTeam(final Player player) {
        return (teamOne.contains(player) || teamTwo.contains(player));
    }

    public boolean hasTeamNone(final Player player) {
        return noTeam.contains(player);
    }

    public boolean isInTeam(final TeamType teamType, final Player player) {
        return this.getPlayerTeam(player) == teamType;
    }

    public List<Player> getPlayersInTeam(final TeamType teamType) {
        final List<Player> output = new ArrayList<>();
        if (teamType == TeamType.TEAM_1)
            output.addAll(teamOne);
        else
            output.addAll(teamTwo);
        return output;
    }

    public TeamType getPlayerTeam(final Player player) {
        if (teamOne.contains(player))
            return TeamType.TEAM_1;
        if (teamTwo.contains(player))
            return TeamType.TEAM_2;
        return TeamType.NONE;
    }

}
