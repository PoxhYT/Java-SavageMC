package main.java.de.xenodev.cbs.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SBManager {
	
	public static void setScoreboard(Player p) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("aaa", "bbb");

		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§8>> §e§lSkyBeasts §8<<");
		
		obj.getScore("§1 ").setScore(18);

		obj.getScore("§8§l◆ §7§lRang").setScore(16);
		obj.getScore("§9JE").setScore(15);

		obj.getScore("§6 ").setScore(13);

		obj.getScore("§8§l◆ §7§lMoney").setScore(11);
		obj.getScore(updateTeam(board, "Money", " §6" + MoneyAPI.getMoneyString(p), "", ChatColor.DARK_AQUA)).setScore(10);

		obj.getScore("§3 ").setScore(8);

		obj.getScore("§8§l◆ §7§lBank").setScore(6);
		if(MoneyAPI.hasBank(p) == true){
			obj.getScore(updateTeam(board, "Bank", " §e" + MoneyAPI.getBankString(p), "", ChatColor.DARK_GREEN)).setScore(5);
		}else{
			obj.getScore(updateTeam(board, "Bank", " §e" + "Kein Konto", "", ChatColor.DARK_GREEN)).setScore(5);
		}

		obj.getScore("§5 ").setScore(3);

		obj.getScore("§8§l◆ §7§lSpielzeit").setScore(1);
		obj.getScore(updateTeam(board, "Time"," §f" + TimeManager.getHours(p), " §6Stunden", ChatColor.BLUE)).setScore(0);
		
		p.setScoreboard(board);
	}
	
	public static void updateScoreboard(Player p) {
		Scoreboard board = p.getScoreboard();
		Objective obj = board.getObjective("aaa");

		obj.getScore(updateTeam(board, "Money", " §6" + MoneyAPI.getMoneyString(p), "", ChatColor.DARK_AQUA)).setScore(10);
		if(MoneyAPI.hasBank(p) == true){
			obj.getScore(updateTeam(board, "Bank", " §e" + MoneyAPI.getBankString(p), "", ChatColor.DARK_GREEN)).setScore(5);
		}else{
			obj.getScore(updateTeam(board, "Bank", " §e" + "Kein Konto", "", ChatColor.DARK_GREEN)).setScore(5);
		}
		obj.getScore(updateTeam(board, "Time", " §f" + TimeManager.getHours(p), " §6Stunden", ChatColor.BLUE)).setScore(0);
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
