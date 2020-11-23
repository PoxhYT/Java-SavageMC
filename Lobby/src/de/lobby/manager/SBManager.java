package de.lobby.manager;

import com.mongodb.client.MongoDatabase;
import com.rosemite.services.helper.Log;
import com.rosemite.services.main.MainService;
import com.rosemite.services.services.coin.CoinService;
import de.lobby.main.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.UUID;

import static de.lobby.main.Main.onlinePlayers;

public class SBManager {

	private static MainService service = MainService.getService(null);

	public void setScoreboard(Player p) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("aaa", "bbb");

		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§eSavageMC§7.§enet");
		
		obj.getScore("§1 ").setScore(9);

		CachedMetaData metaData = Main.luckPerms.getPlayerAdapter(Player.class).getMetaData(p);
		String prefix = metaData.getPrefix();

		obj.getScore("§8• §fRang").setScore(8);
		obj.getScore(prefix).setScore(7);

		obj.getScore("§6 ").setScore(6);

		HashMap<CoinService, UUID> coinServiceMap = new HashMap<>();
		CoinService coinService = service.getCoinService();
		if(coinService == null) {
			coinService = new CoinService(Main.getInstance().mongoDatabase, service);
			Log.d("NICE");
		}
		coinServiceMap.put(coinService, p.getUniqueId());

		obj.getScore("§8• §fCoins").setScore(5);
		obj.getScore(updateTeam(board, "Coins", " §e" + coinService.getCoinAmount(p.getUniqueId().toString()), "", ChatColor.DARK_AQUA)).setScore(4);

		Integer onlinePlayers = Main.onlinePlayers.size();
		Integer maxPlayer = 100;

		obj.getScore("§2 ").setScore(3);

		obj.getScore("§8• §fOnline").setScore(2);
		obj.getScore(updateTeam(board, "Online", " §a" + Main.onlinePlayers.size() + " §7/ §c" + Bukkit.getMaxPlayers(), "", ChatColor.DARK_GREEN)).setScore(1);

		obj.getScore("§3 ").setScore(0);

		p.setScoreboard(board);
	}
	
	public static void updateScoreboard(Player p) {
		Scoreboard board = p.getScoreboard();
		Objective obj = board.getObjective("aaa");

		HashMap<CoinService, UUID> coinServiceMap = new HashMap<>();
		CoinService coinService = service.getCoinService();
		if(coinService == null) {
			coinService = new CoinService(Main.getInstance().mongoDatabase, service);
			Log.d("NICE");
		}
		coinServiceMap.put(coinService, p.getUniqueId());

		CachedMetaData metaData = Main.luckPerms.getPlayerAdapter(Player.class).getMetaData(p);
		String prefix = metaData.getPrefix();

		obj.getScore(updateTeam(board, "Coins", " §e" + coinService.getCoinAmount(p.getUniqueId().toString()), "", ChatColor.DARK_AQUA)).setScore(4);
		obj.getScore(updateTeam(board, "Online", " §a" + onlinePlayers.size() + " §7/ §c" + Bukkit.getMaxPlayers(), "", ChatColor.DARK_GREEN)).setScore(1);

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
