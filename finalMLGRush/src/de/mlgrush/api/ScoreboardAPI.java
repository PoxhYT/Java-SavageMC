package de.mlgrush.api;

import de.mlgrush.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardAPI {

    private Main instance;
    private ScoreboardAPI scoreboardAPI;

    public ScoreboardAPI(Main instance) {
        this.instance = instance;
    }

    public void setScoreboard(Player player) {

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("aaa", "bbb");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§8• §r§eSavageMC§7.§r§enet");
        obj.getScore("§1 ").setScore(9);
        obj.getScore("§8• §r§fDein Rang").setScore(8);
        obj.getScore(updateTeam(board, "Prefix", "§8➥ §a" + prefix, " §4", ChatColor.GREEN)).setScore(7);
        obj.getScore("§2 ").setScore(6);
        obj.getScore("§8• §r§fOnline").setScore(5);
        obj.getScore(updateTeam(board, "Online", "§8➥ §a" + Bukkit.getOnlinePlayers().size() + "§7 / §c" + Bukkit.getMaxPlayers(), " §4", ChatColor.DARK_GRAY)).setScore(4);
        obj.getScore("§5 ").setScore(3);
        obj.getScore("§8• §r§fDeine Coins").setScore(2);
        obj.getScore(updateTeam(board, "Coins", "§8➥ §c" + CoinsAPI.getCoins(player.getUniqueId().toString()), " §fCoins", ChatColor.DARK_RED)).setScore(1);
        obj.getScore("§3 ").setScore(0);
        player.setScoreboard(board);
    }

    public static void updateScoreboard(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective obj = board.getObjective("aaa");
        obj.getScore(updateTeam(board, "Online", "§8➥ §a" + Bukkit.getOnlinePlayers().size() + "§7 / §c" + Bukkit.getMaxPlayers(), " §4", ChatColor.DARK_GRAY)).setScore(4);
        obj.getScore(updateTeam(board, "Coins", "§8➥ §c" + CoinsAPI.getCoins(player.getUniqueId().toString()), " §fCoins", ChatColor.DARK_RED)).setScore(1);

    }

    public static Team getTeam(Scoreboard board, String Team, String prefix, String suffix) {
        Team team = board.getTeam(Team);
        if(team == null) {
            team = board.registerNewTeam(Team);
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.setAllowFriendlyFire(true);
        team.setCanSeeFriendlyInvisibles(true);

        return team;
    }

    public static String updateTeam(Scoreboard board, String Team, String prefix, String suffix, ChatColor entry) {
        Team team = board.getTeam(Team);
        if(team == null) {
            team = board.registerNewTeam(Team);
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        team.addEntry(entry.toString());

        return entry.toString();
    }
}
