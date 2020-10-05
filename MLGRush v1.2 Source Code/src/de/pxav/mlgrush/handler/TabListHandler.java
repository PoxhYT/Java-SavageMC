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
import org.bukkit.scoreboard.Scoreboard;

public class TabListHandler {

    public void setTabList(final Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        if (scoreboard.getTeam("a") == null) {
            scoreboard.registerNewTeam("a").setPrefix("§a§l#1 §8➜ §7");
            scoreboard.registerNewTeam("b").setPrefix("§a§l#2 §8➜ §7");
            scoreboard.registerNewTeam("c").setPrefix("§7");
            scoreboard.registerNewTeam("d").setPrefix("§aSpec §8➜ §7");
        }

        final String team = this.getPlayerTeam(player);
        scoreboard.getTeam(team).addEntry(player.getName());

        for (Player current : Bukkit.getOnlinePlayers()) {
            if (!current.getName().equalsIgnoreCase(player.getName())) {
                current.getScoreboard().getTeam(team).addEntry(player.getName());
                player.getScoreboard().getTeam(this.getPlayerTeam(current)).addEntry(current.getName());
            }
        }
    }

    private String getPlayerTeam(final Player player) {
        if (MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_1) {
            return "a";
        } else if(MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_2) {
            return "b";
        } else if(MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.NONE) {
            return "c";
        } else {
            return "d";
        }
    }

}
