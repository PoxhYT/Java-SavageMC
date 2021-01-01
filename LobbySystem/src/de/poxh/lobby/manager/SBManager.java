package de.poxh.lobby.manager;

import com.rosemite.models.service.common.IService;
import com.rosemite.services.main.MainService;
import de.poxh.lobby.main.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SBManager {

    private static IService service;
    private LuckPerms luckPerms;

    public void setLobbyBoard(Player player) {

        service = MainService.getService(service);

        //Getting players prefix
        CachedMetaData metaData = Main.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        String prefix = metaData.getPrefix();

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("aaa", "bbb");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§8• §e§lSavageMC§7§l.§e§lnet §8•");
        obj.getScore("§1 ").setScore(12);
        obj.getScore("§8• §fRang").setScore(11);
        obj.getScore(updateTeam(board, "Rang", "§7" + "§8➥ " + prefix, "", ChatColor.BLUE)).setScore(10);
        obj.getScore("§5 ").setScore(9);
        obj.getScore("§8• §fCoins").setScore(8);
        obj.getScore(updateTeam(board, "Coins", "§7" + "➥ §e" + getPlayerCoins(player), "", ChatColor.AQUA)).setScore(7);
        obj.getScore("§2 ").setScore(6);
        obj.getScore("§8• §fOnline Freunde").setScore(5);
        obj.getScore("§7➥ §a0§7/§c0").setScore(4);
        obj.getScore("§3 ").setScore(3);
        obj.getScore("§8• §fSpieler").setScore(2);
        obj.getScore(updateTeam(board, "Players", "§7" + "➥ §a" + Bukkit.getOnlinePlayers().size() + "§7/§a" + Bukkit.getMaxPlayers(), "", ChatColor.BLACK)).setScore(1);
        obj.getScore("§4 ").setScore(0);

        player.setScoreboard(board);
    }


    public static void updateScoreboard(Player player) {
        //Getting players prefix
        CachedMetaData metaData = Main.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        String prefix = metaData.getPrefix();
        service = MainService.getService(service);
        Scoreboard board = player.getScoreboard();
        Objective obj = board.getObjective("aaa");
        obj.getScore(updateTeam(board, "Rang", "§7" + "§8➥ " + prefix, "", ChatColor.BLUE)).setScore(10);
        obj.getScore(updateTeam(board, "Coins", "§7" + "➥ §e" + getPlayerCoins(player), "", ChatColor.AQUA)).setScore(7);
        obj.getScore(updateTeam(board, "Players", "§7" + "➥ §a" + Bukkit.getOnlinePlayers().size() + "§7/§c" + Bukkit.getMaxPlayers(), "", ChatColor.BLACK)).setScore(1);
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

    private static int getPlayerCoins(Player player) {
        service = MainService.getService(service);
        return service.getCoinService().getCoinAmount(player.getUniqueId().toString());
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
