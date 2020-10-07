package de.soup.manager;

import de.magnus.coinsapi.util.CoinsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;

public class SBManager {

    static File file = new File("plugins/Config", "config.yml");

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    public static void setLobbyBoard(Player player) {

        String MapName = yamlConfiguration.getString("MapName");

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("aaa", "bbb");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§8• §cSoupTraining §8•");
        obj.getScore("§4 ").setScore(9);
        obj.getScore("§8• §r§fCoins").setScore(8);
        obj.getScore(updateTeam(board, "Coins", "§8➥ §7" + CoinsAPI.getCoins(player.getUniqueId().toString()), " §4", ChatColor.DARK_RED)).setScore(7);
        obj.getScore("§1 ").setScore(6);
        obj.getScore("§8• §r§fOnline").setScore(5);
        obj.getScore(updateTeam(board, "Online", "§8➥ §a" + Bukkit.getOnlinePlayers().size() + "§7 / §c" + Bukkit.getMaxPlayers(), " §4", ChatColor.DARK_GRAY)).setScore(4);
        obj.getScore("§2 ").setScore(3);
        obj.getScore("§8• §r§fRekord").setScore(2);
        obj.getScore("§8➥ §cSOON").setScore(1);

        obj.getScore("§3 ").setScore(0);

        player.setScoreboard(board);
    }

    public static void updateScoreboard(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective obj = board.getObjective("aaa");
        obj.getScore(updateTeam(board, "Online", "§8➥ §a" + Bukkit.getOnlinePlayers().size() + "§7 / §c" + Bukkit.getMaxPlayers(), " §4", ChatColor.DARK_GRAY)).setScore(4);
        obj.getScore(updateTeam(board, "Coins", "§8➥ §e" + CoinsAPI.getCoins(player.getUniqueId().toString()), " §4", ChatColor.DARK_RED)).setScore(7);

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
