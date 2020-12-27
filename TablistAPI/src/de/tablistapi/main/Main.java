package de.tablistapi.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

public class Main extends JavaPlugin implements Listener {
    Scoreboard sb;

    @Override
    public void onEnable() {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();

        sb.registerNewTeam("00000Owner");
        sb.registerNewTeam("00001Mod");
        sb.registerNewTeam("00002Sup");
        sb.registerNewTeam("00003Dev");
        sb.registerNewTeam("00004Builder");
        sb.registerNewTeam("00005YouTuber");
        sb.registerNewTeam("00006Savage");
        sb.registerNewTeam("00007Ultra");
        sb.registerNewTeam("00008Premium+");
        sb.registerNewTeam("00009Premium");
        sb.registerNewTeam("00010Spieler");

        sb.getTeam("00000Owner").setPrefix("§4Owner §7❘ ");
        sb.getTeam("00001Mod").setPrefix("§9Mod §7❘ ");
        sb.getTeam("00002Sup").setPrefix("§3Sup §7❘ ");
        sb.getTeam("00003Dev").setPrefix("§bDev §7❘ ");
        sb.getTeam("00004Builder").setPrefix("§aBuilder §7❘ ");
        sb.getTeam("00005YouTuber").setPrefix("§5YouTuber §7❘ ");
        sb.getTeam("00006Savage").setPrefix("§5Savage §7❘ ");
        sb.getTeam("00007Ultra").setPrefix("§dUltra §7❘ ");
        sb.getTeam("00008Premium+").setPrefix("§6Premium§7+ ❘ ");
        sb.getTeam("00009Premium").setPrefix("§ePremium §7❘ ");
        sb.getTeam("00010Spieler").setPrefix("§7Spieler ❘ ");

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        setPrefix(player);
        event.setJoinMessage(player.getDisplayName() + " §7hat den Server betreten!");
    }

    private void setPrefix(Player player) {
        String team = "";

        if(player.hasPermission("server.owner")) {
            team = "00000Owner";
        }
        if(player.hasPermission("server.Mod")) {
            team = "00001Mod";
        }
        if(player.hasPermission("server.Sup")) {
            team = "00002Sup";
        }
        if(player.hasPermission("server.dev")) {
            team = "00003Dev";
        }
        if(player.hasPermission("server.builder")) {
            team = "00004Builder";
        }
        if(player.hasPermission("server.youtuber")) {
            team = "00005YouTuber";
        }
        if(player.hasPermission("server.savage")) {
            team = "00006Savage";
        }
        if(player.hasPermission("server.ultra")) {
            team = "00007Ultra";
        }
        if(player.hasPermission("server.premium+")) {
            team = "00008Premium+";
        }
        if(player.hasPermission("server.premium")) {
            team = "00009Premium";
        }
        if(player.hasPermission("server.spieler")) {
            team = "00010Spieler";
        }
        sb.getTeam(team).addPlayer(player);
        player.setDisplayName(sb.getTeam(team).getPrefix() + player.getName());

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(sb);
        }
    }
}

