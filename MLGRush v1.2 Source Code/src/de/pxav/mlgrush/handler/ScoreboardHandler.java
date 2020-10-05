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
import de.pxav.mlgrush.enums.GameState;
import de.pxav.mlgrush.enums.TeamType;
import de.pxav.mlgrush.gamestates.GameStateHandler;
import de.pxav.mlgrush.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardHandler {

    public void setScoreboard(final Player player) {
        final PlayerManager playerManager = new PlayerManager(player);
        final Scoreboard scoreboard;
        final Objective objective;
        final Team playerTeam;
        final Team ingamePlayerTeam;
        final Team roundDeaths;
        final Team points;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("main", "dummy");
        playerTeam = scoreboard.registerNewTeam("playerTeam");
        ingamePlayerTeam = scoreboard.registerNewTeam("ingamePlayerTeam");
        roundDeaths = scoreboard.registerNewTeam("deaths");
        points = scoreboard.registerNewTeam("points");


        objective.setDisplayName("  §8§l« §3§lMLGRUSH §8§l»  ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        if (GameStateHandler.getGameState() == GameState.LOBBY) {

            objective.getScore("§d").setScore(6);
            objective.getScore("§7Aktuelle Karte").setScore(5);
            objective.getScore("§8➜ §a" + MLGRush.getInstance().getConfigManager().getMapName()).setScore(4);
            objective.getScore("§d§b§c§n§m§6").setScore(3);
            objective.getScore("§7Dein Team").setScore(2);
            objective.getScore("§1§a").setScore(1);
            objective.getScore("§e").setScore(0);

            playerTeam.addEntry("§1§a");
            if(MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_1)
                playerTeam.setPrefix("§8➜ §aTEAM #1");
            else if(MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_2)
                playerTeam.setPrefix("§8➜ §aTEAM #2");
            else
                playerTeam.setPrefix("§8➜ §cKeins");

        } else if (GameStateHandler.getGameState() == GameState.INGAME || GameStateHandler.getGameState() == GameState.ENDING) {

            objective.getScore("").setScore(9);
            objective.getScore("§7Dein Team").setScore(8);
            objective.getScore("§m§r§f§k").setScore(7);
            objective.getScore("§d").setScore(6);
            objective.getScore("§7Tode").setScore(5);
            objective.getScore("§1§a§k").setScore(4);
            objective.getScore("§e").setScore(3);
            objective.getScore("§7Punkte").setScore(2);
            objective.getScore("§e§l§a§r").setScore(1);
            objective.getScore("§e§l§k").setScore(0);

            roundDeaths.addEntry("§1§a§k");
            roundDeaths.setPrefix("§8➜ §a" + playerManager.getRoundDeaths());

            points.addEntry("§e§l§a§r");
            points.setPrefix("§8➜ " + MLGRush.getInstance().getPointsHandler().getGlobalPoints());


            ingamePlayerTeam.addEntry("§m§r§f§k");
            if (MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_1)
                ingamePlayerTeam.setPrefix("§8➜ §aTEAM #1" + "§b");
            else if (MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_2)
                ingamePlayerTeam.setPrefix("§8➜ §aTEAM #2");
            else
                ingamePlayerTeam.setPrefix("§8➜ §cKeins");
        }
        player.setScoreboard(scoreboard);
    }

    private void update(final Player player) {
        final PlayerManager playerManager = new PlayerManager(player);

        if(GameStateHandler.getGameState() == GameState.LOBBY) {

            final Team playerTeam = player.getScoreboard().getTeam("playerTeam");
            if (MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_1)
                playerTeam.setPrefix("§8➜ §aTEAM #1");
            else if (MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_2)
                playerTeam.setPrefix("§8➜ §aTEAM #2§c");
            else
                playerTeam.setPrefix("§8➜ §cKeins");

        } else if(GameStateHandler.getGameState() == GameState.INGAME) {

            final Team roundDeaths = player.getScoreboard().getTeam("deaths");
            roundDeaths.setPrefix("§8➜ §a" + playerManager.getRoundDeaths());

            final Team points = player.getScoreboard().getTeam("points");
            points.setPrefix("§8➜ " + MLGRush.getInstance().getPointsHandler().getGlobalPoints());

            final Team ingamePlayerTeam = player.getScoreboard().getTeam("ingamePlayerTeam");
            if(MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_1)
                ingamePlayerTeam.setPrefix("§8➜ §aTEAM #1");
            else if(MLGRush.getInstance().getTeamHandler().getPlayerTeam(player) == TeamType.TEAM_2)
                ingamePlayerTeam.setPrefix("§8➜ §aTEAM #2" + "§b");
            else
                ingamePlayerTeam.setPrefix("§8➜ §cKeins");

        }
    }

    public void startUpdater() {
        Bukkit.getScheduler().runTaskTimer(MLGRush.getInstance(), () -> {
            for (Player player : MLGRush.getInstance().getTeamHandler().playing)
                this.update(player);
        }, 0, 2);
    }

}
