package de.mlgrush.handler;

import de.mlgrush.enums.TeamType;
import de.mlgrush.main.Main;
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
        if (Main.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_1) {
            return "a";
        } else if(Main.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_2) {
            return "b";
        } else if(Main.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.NONE) {
            return "c";
        } else {
            return "d";
        }
    }

}

