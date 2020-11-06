package de.sw.manager;

import com.rosemite.services.main.MainService;
import com.rosemite.services.models.skywars.PlayerSkywarsStats;
import de.sw.enums.Path;
import de.sw.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SBManager {

    private Main instance;
    private static File fileSkywars = new File("plugins/SkyWars", "MapData.yml");
    private static YamlConfiguration yamlConfigurationSkyWars = YamlConfiguration.loadConfiguration(fileSkywars);
    private static MainService service;

    public void setLobbyBoard(Player player) {

        service = MainService.getService(service);

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("aaa", "bbb");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§8• §bSkyWars §8•");
        obj.getScore("§1 ").setScore(9);
        obj.getScore("§8• §fMap").setScore(8);
        obj.getScore("§8➥ §e" + Main.MapName1.get(Path.MapName.toString())).setScore(7);
        obj.getScore("§2 ").setScore(6);
        obj.getScore("§8• §fSpielvariante").setScore(5);
        obj.getScore("§8➥ §8(§e" + Main.MapName1.get(Path.GameSize.toString()) + "§8)").setScore(4);
        obj.getScore("§3 ").setScore(3);
        obj.getScore("§8• §fKit").setScore(2);
        obj.getScore(updateTeam(board, "Kit", "§8➥ §e" + service.getSkywarsService().getLatestSelectedKit(player.getUniqueId().toString()), " §4", ChatColor.DARK_GRAY)).setScore(1);
        obj.getScore("§4 ").setScore(0);

        player.setScoreboard(board);
    }

    public void setIngameBoard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("aaa", "bbb");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§8• §bSkyWars §8•");
        obj.getScore("§1 ").setScore(9);
        obj.getScore("§8• §fMap").setScore(8);
        obj.getScore("§8➥ §e" + Main.MapName1.get(Path.MapName.toString())).setScore(7);
        obj.getScore("§2 ").setScore(6);
        obj.getScore("§8• §fKills").setScore(5);
        if (Main.roundKills.get(player.getUniqueId()) == null) {
            obj.getScore("§8➥ §e 0").setScore(4);
        } else {
            obj.getScore("§8➥ §e" + Main.roundKills.get(player.getUniqueId()).getKills()).setScore(4);
        }
        obj.getScore("§3 ").setScore(3);
        obj.getScore("§8• §fKit").setScore(2);
        obj.getScore(updateTeam(board, "currentKit", "§8➥ §e" + service.getSkywarsService().getLatestSelectedKit(player.getUniqueId().toString()), " §4", ChatColor.DARK_GRAY)).setScore(1);
        obj.getScore("§4 ").setScore(0);

        player.setScoreboard(board);
    }

    public static void updateScoreboard(Player player) {
        service = MainService.getService(service);
        Scoreboard board = player.getScoreboard();
        Objective obj = board.getObjective("aaa");
        obj.getScore(updateTeam(board, "Kit", "§8➥ §e" + service.getSkywarsService().getLatestSelectedKit(player.getUniqueId().toString()), " §4", ChatColor.DARK_GRAY)).setScore(1);

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
